package com.example.mediasearchingapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.commonModelUtil.data.MediaType
import com.example.commonModelUtil.extension.layoutInflater
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.extension.toPx
import com.example.mediasearchingapp.R
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemSearchImageBinding
import com.example.mediasearchingapp.databinding.ItemSearchVideoBinding

class SearchAdapter(
    private val context: Context,
    private val onClick: (Boolean, SearchListData) -> Unit
) : BaseAdapter<ViewDataBinding, SearchListData>() {

    var recyclerView: RecyclerView? = null

    fun updateFavoriteBtn(favList: List<SearchListData>) {
        adapterList.forEachIndexed { index, data ->
            if (data.isFavorite && data !in favList) {
                adapterList[index].isFavorite = false
                (recyclerView?.findViewHolderForAdapterPosition(index) as? BaseMediaViewHolder<*, *>)?.run {
                    updateFavoriteBtnView()
                }
            }
        }
    }

    fun addSearchList(list: List<SearchListData>) {
        val startPosition = adapterList.lastIndex
        adapterList.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
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
        open fun unselectFavorite() = Unit

        fun onItemClick(view: View) {
            view.isSelected = !data.isFavorite
            data.isFavorite = !data.isFavorite
            onClick.invoke(data.isFavorite, data)
        }

        fun updateFavoriteBtnView() {
            unselectFavorite()
        }
    }

    inner class ImageViewHolder(
        binding: ItemSearchImageBinding
    ) : BaseMediaViewHolder<ItemSearchImageBinding, SearchListData.ImageDocumentData>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            val thumbWidth = context.resources.getDimension(R.dimen.search_thumbnail_width).toInt()
            val height = (data.height * thumbWidth / data.width).takeIf { it <= 300.toPx(context) } ?: 300.toPx(context)
            ivThumbnail.layoutParams.height = height
            imageData = data
            btnFavorite.apply {
                isSelected = data.isFavorite
                setOnClickListener {
                    onItemClick(it)
                }
            }
        }

        override fun unselectFavorite() = with(binding) {
            btnFavorite.isSelected = false
        }
    }

    inner class VideoViewHolder(
        binding: ItemSearchVideoBinding
    ) : BaseMediaViewHolder<ItemSearchVideoBinding, SearchListData.VideoDocumentData>(binding) {
        override fun initViewHolder(): Unit = with(binding) {
            val thumbWidth = context.resources.getDimension(R.dimen.search_thumbnail_width).toInt()
            val height = 78 * thumbWidth / 138
            ivThumbnail.layoutParams.height = height
            videoData = data
            btnFavorite.apply {
                isSelected = data.isFavorite
                setOnClickListener {
                    onItemClick(it)
                }
            }
        }

        override fun unselectFavorite() = with(binding) {
            btnFavorite.isSelected = false
        }
    }
}