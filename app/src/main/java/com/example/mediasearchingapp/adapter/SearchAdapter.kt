package com.example.mediasearchingapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.commonModelUtil.extension.layoutInflater
import com.example.commonModelUtil.search.SearchListData
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemSearchImageBinding
import com.example.mediasearchingapp.databinding.ItemSearchVideoBinding

class SearchAdapter(
    private val context: Context,
) : BaseAdapter<ViewDataBinding, SearchListData>() {

    companion object {
        enum class SearchType {
            IMAGE, VIDEO
        }
    }

    fun addSearchList(list: List<SearchListData>) {
        val startPosition = adapterList.lastIndex
        adapterList.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterList.getOrNull(position)) {
            is SearchListData.ImageDocumentData -> SearchType.IMAGE.ordinal
            else -> SearchType.VIDEO.ordinal
        }
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (viewType) {
            SearchType.IMAGE.ordinal -> {
                ImageViewHolder(
                    ItemSearchImageBinding.inflate(context.layoutInflater, parent, false)
                )
            }
            else -> {
                VideoViewHolder(
                    ItemSearchVideoBinding.inflate(context.layoutInflater, parent, false)
                )
            }
        }
    }

    abstract inner class BaseMediaViewHolder<T : ViewDataBinding, V : SearchListData>(
        override val binding: T
    ) : BaseViewHolder<ViewDataBinding>(binding) {
        lateinit var data: V
        override fun bind(position: Int) {
            data = adapterList[position] as V
            initViewHolder()
        }

        abstract fun initViewHolder()
    }

    inner class ImageViewHolder(
        binding: ItemSearchImageBinding
    ) : BaseMediaViewHolder<ItemSearchImageBinding, SearchListData.ImageDocumentData>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            imageData = data
        }
    }

    inner class VideoViewHolder(
        binding: ItemSearchVideoBinding
    ) : BaseMediaViewHolder<ItemSearchVideoBinding, SearchListData.VideoDocumentData>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            videoData = data
        }
    }
}