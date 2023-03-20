package com.example.mediasearchingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.commonModelUtil.extension.getWindowWidth
import com.example.mediasearchingapp.base.BaseFragment
import com.example.mediasearchingapp.databinding.FragmentSearchBinding
import com.example.mediasearchingapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val enableBackPressed = true
    private val viewModel: SearchViewModel by viewModels()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

    override fun initFragment(savedInstanceState: Bundle?): Unit = with(binding) {
        with(rvSearch) {
            val columnCnt = requireActivity().getWindowWidth() / 160.dp
            layoutManager = GridLayoutManager(requireContext(), columnCnt)
            adapter
        }
    }
}