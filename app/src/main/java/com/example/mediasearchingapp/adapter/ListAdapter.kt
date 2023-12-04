package com.example.mediasearchingapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.coreDomain.data.SearchListData
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemListImageBinding
import com.example.mediasearchingapp.databinding.ItemListVideoBinding
import com.example.mediasearchingapp.extension.layoutInflater

class ListAdapter(
    private val onClick: (SearchListData) -> Unit
) : BaseAdapter<ViewDataBinding, SearchListData>() {

    companion object {
        enum class ListType {
            IMAGE, VIDEO
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterList.getOrNull(position)) {
            is SearchListData.ImageDocumentData -> ListType.IMAGE.ordinal
            else -> ListType.VIDEO.ordinal
        }
    }

    override fun areItemsSame(oldItem: SearchListData, newItem: SearchListData): Boolean {
        return oldItem.thumbnail == newItem.thumbnail
    }

    override fun areContentsSame(oldItem: SearchListData, newItem: SearchListData): Boolean {
        return oldItem == newItem
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (viewType) {
            ListType.IMAGE.ordinal -> {
                ImageViewHolder(
                    ItemListImageBinding.inflate(parent.context.layoutInflater, parent, false)
                )
            }
            else -> {
                VideoViewHolder(
                    ItemListVideoBinding.inflate(parent.context.layoutInflater, parent, false)
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

        fun onItemClick(view: View) {
            view.isSelected = !data.isFavorite
            onClick.invoke(data)
        }
    }

    inner class ImageViewHolder(
        binding: ItemListImageBinding
    ) : BaseMediaViewHolder<ItemListImageBinding, SearchListData.ImageDocumentData>(binding) {

        init {
            binding.btnFavorite.setOnClickListener {
                val data = adapterList[absoluteAdapterPosition]
                it.isSelected = !data.isFavorite
                onClick.invoke(data)
            }
        }

        override fun initViewHolder(): Unit = with(binding) {
            imageData = data
            btnFavorite.isSelected = data.isFavorite
        }
    }

    inner class VideoViewHolder(
        binding: ItemListVideoBinding
    ) : BaseMediaViewHolder<ItemListVideoBinding, SearchListData.VideoDocumentData>(binding) {

        init {
            binding.btnFavorite.setOnClickListener {
                val data = adapterList[absoluteAdapterPosition]
                it.isSelected = !data.isFavorite
                onClick.invoke(data)
            }
        }

        override fun initViewHolder(): Unit = with(binding) {
            videoData = data
            btnFavorite.isSelected = data.isFavorite
        }
    }
}