package com.example.mediasearchingapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.commonModelUtil.data.MediaType
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.extension.layoutInflater
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemListImageBinding
import com.example.mediasearchingapp.databinding.ItemListVideoBinding

class ListAdapter(
    private val context: Context,
    private val onClick: (Boolean, SearchListData) -> Unit
) : BaseAdapter<ViewDataBinding, SearchListData>() {

    fun updateList(items: List<SearchListData>) {
        val diffUtilCallBack = DiffUtilCallback(adapterList, items)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)

        adapterList.run {
            clear()
            addAll(items)
            diffResult.dispatchUpdatesTo(this@ListAdapter)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterList.getOrNull(position)) {
            is SearchListData.ImageDocumentData -> MediaType.IMAGE.ordinal
            else -> MediaType.VIDEO.ordinal
        }
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (viewType) {
            MediaType.IMAGE.ordinal -> {
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
            data = adapterList[position] as V
            initViewHolder()
        }

        abstract fun initViewHolder()

        fun onItemClick(view: View) {
            view.isSelected = !data.isFavorite
            data.isFavorite = !data.isFavorite
            onClick.invoke(data.isFavorite, data)
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

    class DiffUtilCallback(
        private val oldList: List<SearchListData>,
        private val newList: List<SearchListData>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.thumbnail == newItem.thumbnail && oldItem.isFavorite == newItem.isFavorite
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}