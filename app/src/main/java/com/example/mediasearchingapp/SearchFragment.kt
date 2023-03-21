package com.example.mediasearchingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commonModelUtil.extension.getWindowWidth
import com.example.commonModelUtil.extension.onEachState
import com.example.mediasearchingapp.adapter.SearchAdapter
import com.example.mediasearchingapp.base.BaseFragment
import com.example.mediasearchingapp.databinding.FragmentSearchBinding
import com.example.mediasearchingapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val enableBackPressed = true
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy {
        SearchAdapter(requireContext())
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
        with(rvSearch) {
            val columnCnt = requireActivity().getWindowWidth() / 150.px
            layoutManager = GridLayoutManager(requireContext(), columnCnt)
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
    }

    private fun collectViewModel() = with(searchViewModel) {
        searchResult.onEachState(
            success = {
                if (isNewQuerySearch) {
                    searchAdapter.set(it)
                    isNewQuerySearch = false
                } else {
                    searchAdapter.addSearchList(it)
                }
            },
            error = {
                Log.i("아현", "searchResult : $this")
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
}