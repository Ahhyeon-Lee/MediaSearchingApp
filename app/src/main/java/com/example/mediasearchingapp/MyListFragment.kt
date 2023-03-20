package com.example.mediasearchingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mediasearchingapp.base.BaseFragment
import com.example.mediasearchingapp.databinding.FragmentMyListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : BaseFragment<FragmentMyListBinding>() {

    override val enableBackPressed = true

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyListBinding = FragmentMyListBinding.inflate(inflater, container, false)


}