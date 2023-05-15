package com.example.mediasearchingapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.commonModelUtil.extension.getWindowWidth
import com.example.commonModelUtil.extension.onEachState
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.extension.getDimenInt
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
            searchViewModel.putFavoriteData(data)
        } else {
//            preferenceUtil.deleteFavoriteData(data.thumbnail)
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
            layoutManager = getStaggeredLayoutManager()
            itemAnimator = null
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

        btnTypeDelete.setOnClickListener {
            etSearch.text.clear()
        }

        btnUp.setOnClickListener {
            if (it.alpha != 0f) {
                rvSearch.scrollToPosition(0)
            }
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
            if (!recyclerView.canScrollVertically(1) && searchAdapter.itemCount > 0) {
                searchViewModel.search()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
            val positions = layoutManager.findFirstVisibleItemPositions(
                IntArray(layoutManager.spanCount)
            )
            searchViewModel.showBtnUp(positions[0] > 9)
        }
    }

    private fun getStaggeredLayoutManager(): StaggeredGridLayoutManager {
        val thumbWidth = requireContext().getDimenInt(R.dimen.search_thumbnail_width)
        val columnCnt = requireActivity().getWindowWidth() / thumbWidth
        return StaggeredGridLayoutManager(columnCnt, LinearLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }
    }

    override fun onResume() {
        super.onResume()
        searchAdapter.updateFavoriteBtn(searchViewModel.getFavoriteList())
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation != searchViewModel.prevOrientation) {
            binding.rvSearch.layoutManager = getStaggeredLayoutManager()
        }
        searchViewModel.prevOrientation = newConfig.orientation
    }
}