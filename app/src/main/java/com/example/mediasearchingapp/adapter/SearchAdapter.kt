package com.example.mediasearchingapp.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.commonModelUtil.extension.setImage
import com.example.commonModelUtil.search.SearchData
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemSearchBinding

class SearchAdapter : BaseAdapter<ViewDataBinding, SearchData>() {

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        TODO("Not yet implemented")
    }

    abstract inner class BaseMediaViewHolder<T : ViewDataBinding>(
        override val binding: T
    ) : BaseViewHolder<ViewDataBinding>(binding) {

        lateinit var data: SearchData

        override fun bind(position: Int) {
            data = adapterList[position]
            initViewHolder()
        }

        abstract fun initViewHolder()
    }

    inner class ImageViewHolder(
        binding: ItemSearchBinding
    ) : BaseMediaViewHolder<ItemSearchBinding>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            ivThumbnail.setImage(data.imageData.thumbnailUrl)
        }
    }
}