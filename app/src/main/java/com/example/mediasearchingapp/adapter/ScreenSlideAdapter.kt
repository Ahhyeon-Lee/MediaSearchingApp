package com.example.mediasearchingapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mediasearchingapp.MyListFragment
import com.example.mediasearchingapp.SearchFragment

class ScreenSlideAdapter(fa:FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            SearchFragment()
        } else {
            MyListFragment()
        }
    }

}