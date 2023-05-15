package com.example.mediasearchingapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.extension.getDimenInt
import com.example.commonModelUtil.extension.getWindowWidth
import com.example.commonModelUtil.extension.onEachState
import com.example.commonModelUtil.extension.showToast
import com.example.commonModelUtil.util.PreferenceUtil
import com.example.mediasearchingapp.adapter.ListAdapter
import com.example.mediasearchingapp.base.BaseFragment
import com.example.mediasearchingapp.databinding.FragmentMyListBinding
import com.example.mediasearchingapp.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@AndroidEntryPoint
class MyListFragment : BaseFragment<FragmentMyListBinding>() {

    override val enableBackPressed = false
    private val listViewModel: ListViewModel by viewModels()
    private val listAdapter by lazy {
        ListAdapter(requireContext(), onItemClicked)
    }

    private val onItemClicked: (SearchListData) -> Unit = { data ->
        listViewModel.deleteFavoriteData(data)
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyListBinding = FragmentMyListBinding.inflate(inflater, container, false)

    override fun initFragment(savedInstanceState: Bundle?) {
        addListener()
        collectViewModel()
    }

    private fun addListener() = with(binding) {
        rvList.apply {
            layoutManager = getGridLayoutManager()
            adapter = listAdapter
        }
    }

    private fun collectViewModel() = with(listViewModel) {
        favoriteListData.onEachState(
            success = { listAdapter.updateList(it) },
            error = { requireContext().showToast(com.example.commonModelUtil.R.string.error) }
        ).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getGridLayoutManager(): GridLayoutManager {
        val thumbWidth =
            requireContext().getDimenInt(R.dimen.list_thumbnail_width) + requireContext().getDimenInt(
                R.dimen.list_thumbnail_margin_horizontal
            )
        val columnCnt = requireActivity().getWindowWidth() / thumbWidth
        return GridLayoutManager(requireContext(), columnCnt)
    }

    override fun onResume() {
        super.onResume()
        listViewModel.getFavoriteList()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.rvList.layoutManager = getGridLayoutManager()
    }
}