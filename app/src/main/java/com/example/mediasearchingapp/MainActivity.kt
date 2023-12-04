package com.example.mediasearchingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mediasearchingapp.adapter.ScreenSlideAdapter
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

        binding.viewPager.apply {
            adapter = ScreenSlideAdapter(this@MainActivity)
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

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