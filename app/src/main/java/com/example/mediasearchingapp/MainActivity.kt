package com.example.mediasearchingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mediasearchingapp.adapter.ScreenViewPagerAdapter
import com.example.mediasearchingapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ScreenViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.tab_text_search)
                tab.icon = getDrawable(R.drawable.ic_search)
            } else {
                tab.text = getString(R.string.tab_text_my_list)
                tab.icon = getDrawable(R.drawable.ic_archive)
            }
        }.attach()
    }

}