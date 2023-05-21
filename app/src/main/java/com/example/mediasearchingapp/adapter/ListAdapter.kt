package com.example.mediasearchingapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.coreDomain.data.SearchListData
import com.example.mediasearchingapp.extension.layoutInflater
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemListImageBinding
import com.example.mediasearchingapp.databinding.ItemListVideoBinding

class ListAdapter(
    private val context: Context,
    private val onClick: (SearchListData) -> Unit
) : BaseAdapter<ViewDataBinding, SearchListData>() {

    companion object {
        enum class ListType {
            IMAGE, VIDEO
        }
    }

    private val asyncDiffer = AsyncListDiffer(this, DiffUtilCallback())

    fun updateList(items: List<SearchListData>) {
        asyncDiffer.submitList(items)
    }

    override fun getItemCount(): Int = asyncDiffer.currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (asyncDiffer.currentList.getOrNull(position)) {
            is SearchListData.ImageDocumentData -> ListType.IMAGE.ordinal
            else -> ListType.VIDEO.ordinal
        }
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (viewType) {
            ListType.IMAGE.ordinal -> {
                ImageViewHolder(
                    ItemListImageBinding.inflate(context.layoutInflater, parent, false)
                )
            }
            else -> {
                VideoViewHolder(
                    ItemListVideoBinding.inflate(context.layoutInflater, parent, false)
                )
            }
        }
    }

    abstract inner class BaseMediaViewHolder<T : ViewDataBinding, V : SearchListData>(
        override val binding: T
    ) : BaseViewHolder<ViewDataBinding>(binding) {
        lateinit var data: V
        override fun bind(position: Int) {
            data = asyncDiffer.currentList[position] as V
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
        override fun initViewHolder(): Unit = with(binding) {
            imageData = data
            btnFavorite.apply {
                isSelected = data.isFavorite
                setOnClickListener {
                    onItemClick(it)
                }
            }
        }
    }

    inner class VideoViewHolder(
        binding: ItemListVideoBinding
    ) : BaseMediaViewHolder<ItemListVideoBinding, SearchListData.VideoDocumentData>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            videoData = data
            btnFavorite.apply {
                isSelected = data.isFavorite
                setOnClickListener {
                    onItemClick(it)
                }
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<SearchListData>() {

        override fun areItemsTheSame(oldItem: SearchListData, newItem: SearchListData): Boolean {
            return oldItem.thumbnail == newItem.thumbnail && oldItem.isFavorite == newItem.isFavorite
        }

        override fun areContentsTheSame(oldItem: SearchListData, newItem: SearchListData): Boolean {
            return oldItem == newItem
        }
    }
}