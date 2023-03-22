package com.example.mediasearchingapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.commonModelUtil.extension.layoutInflater
import com.example.commonModelUtil.search.SearchListData
import com.example.mediasearchingapp.R
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemSearchImageBinding
import com.example.mediasearchingapp.databinding.ItemSearchVideoBinding

class SearchAdapter(
    private val context: Context,
    private val onClick: (Boolean, SearchListData) -> Unit
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
    ) : BaseViewHolder<ViewDataBinding>(binding), View.OnClickListener {
        lateinit var data: V
        override fun bind(position: Int) {
            data = adapterList[position] as V
            initViewHolder()
        }

        abstract fun initViewHolder()

        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.btnFavorite -> {
                    onClick.invoke(view.isSelected, data)
                }
            }
        }

        fun updateBtnFavorite() {

        }
    }

    inner class ImageViewHolder(
        binding: ItemSearchImageBinding
    ) : BaseMediaViewHolder<ItemSearchImageBinding, SearchListData.ImageDocumentData>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            imageData = data
            val thumbWidth = context.resources.getDimension(R.dimen.search_thumbnail_width).toInt()
            ivThumbnail.layoutParams.height = data.height * thumbWidth / data.width
            btnFavorite.isSelected = data.isFavorite
        }
    }

    inner class VideoViewHolder(
        binding: ItemSearchVideoBinding
    ) : BaseMediaViewHolder<ItemSearchVideoBinding, SearchListData.VideoDocumentData>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            videoData = data
            btnFavorite.isSelected = data.isFavorite
        }
    }
}