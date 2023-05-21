package com.example.mediasearchingapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.mediasearchingapp.extension.LifecycleOwnerWrapper
import com.example.mediasearchingapp.extension.toDp
import com.example.mediasearchingapp.extension.toPx

abstract class BaseFragment<Binding : ViewDataBinding> : Fragment(), LifecycleOwnerWrapper {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    protected abstract val enableBackPressed: Boolean
    private val backPressedCallback by lazy {
        object : OnBackPressedCallback(enableBackPressed) {
            override fun handleOnBackPressed() {
                finishActivity()
                onFragmentBackPressed()
            }
        }
    }

    protected open fun onFragmentBackPressed(): Unit = Unit

    protected abstract fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding

    protected open fun initFragment(savedInstanceState: Bundle?) = Unit
    protected open fun initBinding() = Unit

    override fun initLifecycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createFragmentBinding(inflater, container).apply {
        lifecycleOwner = viewLifecycleOwner
        _binding = this
        initBinding()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment(savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun finishActivity(): Boolean {
        return if (parentFragmentManager.backStackEntryCount == 1) {
            requireActivity().finish()
            true
        } else false
    }

    val Number.dp: Int get() = this.toDp(requireContext()).toInt()
    val Number.px: Int get() = this.toPx(requireContext())
}