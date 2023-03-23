package com.example.mediasearchingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.commonModelUtil.extension.getWindowWidth
import com.example.commonModelUtil.extension.onEachState
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.extension.showToast
import com.example.commonModelUtil.util.PreferenceUtil
import com.example.mediasearchingapp.adapter.SearchAdapter
import com.example.mediasearchingapp.base.BaseFragment
import com.example.mediasearchingapp.databinding.FragmentSearchBinding
import com.example.mediasearchingapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    @Inject
    lateinit var preferenceUtil: PreferenceUtil
    private val tag = SearchFragment::class.java.simpleName

    override val enableBackPressed = false
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy {
        SearchAdapter(requireContext(), onItemClicked)
    }

    private val onItemClicked: (Boolean, SearchListData) -> Unit = { action, data ->
        if (action) {
            preferenceUtil.putFavoriteData(data)
        } else {
            preferenceUtil.deleteFavoriteData(data.thumbnail)
        }
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

    override fun initBinding() = with(binding) {
        viewModel = searchViewModel
    }

    override fun initFragment(savedInstanceState: Bundle?): Unit = with(binding) {
        addListener()
        collectViewModel()
        rvSearch.apply {
            val thumbWidth = context.resources.getDimension(R.dimen.search_thumbnail_width).toInt()
            val columnCnt = requireActivity().getWindowWidth() / thumbWidth
            layoutManager =
                StaggeredGridLayoutManager(columnCnt, LinearLayoutManager.VERTICAL).apply {
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }
            adapter = searchAdapter
        }
    }

    private fun addListener() = with(binding) {
        with(etSearch) {
            addTextChangedListener {
                searchViewModel.setTyping(it?.isNotEmpty() == true)
            }
            setOnEditorActionListener { textView, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchViewModel.resetNewQuerySearch(textView.text.toString())
                    searchViewModel.search()
                }
                true
            }
        }

        rvSearch.addOnScrollListener(scrollListener)
        rvSearch.itemAnimator = null

        btnTypeDelete.setOnClickListener {
            etSearch.text.clear()
        }
    }

    private fun collectViewModel() = with(searchViewModel) {
        searchResult.onEachState(
            success = {
                if (isNewQuerySearch) {
                    searchAdapter.set(it)
                    binding.rvSearch.scrollToPosition(0)
                    isNewQuerySearch = false
                } else {
                    searchAdapter.addSearchList(it)
                }
            },
            error = {
                requireContext().showToast(com.example.commonModelUtil.R.string.error)
                Log.e(tag, "searchResult error : $it")
            }
        ).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (!recyclerView.canScrollVertically(1)) {
                searchViewModel.search()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        searchAdapter.updateFavoriteBtn(searchViewModel.getFavoriteList())
    }
}