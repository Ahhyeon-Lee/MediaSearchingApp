package com.example.mediasearchingapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mediasearchingapp.extension.layoutInflater
import com.example.coreDomain.data.SearchListData
import com.example.mediasearchingapp.extension.getDimenInt
import com.example.mediasearchingapp.extension.toPx
import com.example.mediasearchingapp.R
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.databinding.ItemSearchImageBinding
import com.example.mediasearchingapp.databinding.ItemSearchPageBinding
import com.example.mediasearchingapp.databinding.ItemSearchVideoBinding

class SearchAdapter(
    private val context: Context,
    private val onClick: (Boolean, SearchListData) -> Unit
) : BaseAdapter<ViewDataBinding, SearchListData>() {

    companion object {
        enum class SearchType {
            IMAGE, VIDEO, PAGE
        }
    }

    var recyclerView: RecyclerView? = null

    fun updateFavoriteBtn(favList: List<SearchListData>) {
        adapterList.forEachIndexed { index, data ->
            if (data.isFavorite && data.thumbnail !in favList.map { it.thumbnail }) {
                adapterList[index].isFavorite = false
                (recyclerView?.findViewHolderForAdapterPosition(index) as? BaseMediaViewHolder<*, *>)?.run {
                    updateFavoriteBtnView()
                }
            }
        }
    }

    fun addSearchList(list: List<SearchListData>) {
        val startPosition = adapterList.size
        adapterList.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterList.getOrNull(position)) {
            is SearchListData.ImageDocumentData -> SearchType.IMAGE.ordinal
            is SearchListData.VideoDocumentData -> SearchType.VIDEO.ordinal
            else -> SearchType.PAGE.ordinal
        }
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (viewType) {
            SearchType.IMAGE.ordinal -> {
                ImageViewHolder(
                    ItemSearchImageBinding.inflate(context.layoutInflater, parent, false)
                )
            }
            SearchType.VIDEO.ordinal -> {
                VideoViewHolder(
                    ItemSearchVideoBinding.inflate(context.layoutInflater, parent, false)
                )
            }
            else -> {
                PageViewHolder(
                    ItemSearchPageBinding.inflate(context.layoutInflater, parent, false)
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
            val thumbWidth = context.getDimenInt(R.dimen.search_thumbnail_width)
            val height = (data.height * thumbWidth / data.width).takeIf { it <= 300.toPx(context) }
                ?: 300.toPx(context)
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
            val thumbWidth = context.getDimenInt(R.dimen.search_thumbnail_width)
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

    inner class PageViewHolder(
        binding: ItemSearchPageBinding
    ) : BaseViewHolder<ItemSearchPageBinding>(binding) {

        init {
            val layoutParams = this.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
        }

        override fun bind(position: Int) {
            val data = adapterList[position] as SearchListData.PageData
            binding.tvPage.text = context.getString(R.string.text_page, data.page)
        }
    }
}