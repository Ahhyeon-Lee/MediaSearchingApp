package com.example.mediasearchingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coreDomain.data.SearchListData
import com.example.mediasearchingapp.adapter.SearchAdapter
import com.example.mediasearchingapp.base.BaseFragment
import com.example.mediasearchingapp.data.UpdateFavoriteActionType
import com.example.mediasearchingapp.databinding.FragmentSearchBinding
import com.example.mediasearchingapp.extension.getDimensionInt
import com.example.mediasearchingapp.extension.getWindowWidth
import com.example.mediasearchingapp.extension.showToast
import com.example.mediasearchingapp.extension.textChangesFlow
import com.example.mediasearchingapp.viewmodel.FavoriteSharedViewModel
import com.example.mediasearchingapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val tag = SearchFragment::class.java.simpleName

    override val enableBackPressed = false
    private val searchViewModel: SearchViewModel by viewModels()
    private val sharedViewModel: FavoriteSharedViewModel by activityViewModels()
    private val searchAdapter by lazy { SearchAdapter(onItemClicked) }

    private val onItemClicked: (UpdateFavoriteActionType, SearchListData) -> Unit =
        { action, data ->
            searchViewModel.editFavoriteItem(action, data)
            sharedViewModel.updateFavoriteData(action, data)
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
            val thumbWidth = requireContext().getDimensionInt(R.dimen.search_thumbnail_width)
            val columnCnt = requireActivity().getWindowWidth() / thumbWidth
            layoutManager = StaggeredGridLayoutManager(columnCnt, LinearLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
            itemAnimator = null
            adapter = searchAdapter
        }
    }

    private fun addListener() = with(binding) {
        with(etSearch) {
            textChangesFlow()
                .onEach {
                    searchViewModel.setTyping(it.isNotEmpty())
                }
                .debounce(500)
                .onEach {
                    if (it.isEmpty()) return@onEach
                    searchViewModel.resetNewQuerySearch(it)
                    searchViewModel.search()
                }.launchIn(lifecycleScope)
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

    private fun collectViewModel() {
        with(searchViewModel) {
            searchResult.observe { searchResult ->
                when (searchResult) {
                    is ResultState.Success -> {
                        searchAdapter.submit(searchResult.data.list)

                        when {
                            searchResult.data.list.isEmpty() -> {
                                requireContext().showToast(R.string.empty_list)
                                searchViewModel.resetNewQuerySearch()
                            }
                            searchResult.data.isNewQuerySearch -> {
                                binding.rvSearch.scrollToPosition(0)
                            }
                        }
                    }
                    is ResultState.Error -> {
                        requireContext().showToast(R.string.error)
                        Log.e(tag, "searchResult error : $searchResult")
                    }
                    else -> {}
                }
            }
        }

        with(sharedViewModel) {
            updateFavoriteDataState.launchInUiState(
                success = {
                    searchViewModel.editFavoriteItem(it.first, it.second)
                }
            )
        }
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
}