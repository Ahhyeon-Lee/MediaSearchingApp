package com.example.mediasearchingapp.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coreDomain.data.SearchListData
import com.example.mediasearchingapp.R
import com.example.mediasearchingapp.base.BaseAdapter
import com.example.mediasearchingapp.base.BaseViewHolder
import com.example.mediasearchingapp.data.UpdateFavoriteActionType
import com.example.mediasearchingapp.databinding.ItemSearchImageBinding
import com.example.mediasearchingapp.databinding.ItemSearchPageBinding
import com.example.mediasearchingapp.databinding.ItemSearchVideoBinding
import com.example.mediasearchingapp.extension.getDimensionInt
import com.example.mediasearchingapp.extension.layoutInflater
import com.example.mediasearchingapp.extension.setImage
import com.example.mediasearchingapp.extension.toPx

class SearchAdapter(
    private val onClick: (UpdateFavoriteActionType, SearchListData) -> Unit
) : BaseAdapter<ViewDataBinding, SearchListData>() {

    companion object {
        enum class SearchType {
            IMAGE, VIDEO, PAGE
        }

        enum class SearchPayLoadType {
            FAVORITE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterList.getOrNull(position)) {
            is SearchListData.ImageDocumentData -> SearchType.IMAGE.ordinal
            is SearchListData.VideoDocumentData -> SearchType.VIDEO.ordinal
            else -> SearchType.PAGE.ordinal
        }
    }

    override fun areItemsSame(oldItem: SearchListData, newItem: SearchListData): Boolean {
        return oldItem.thumbnail == newItem.thumbnail
    }

    override fun areContentsSame(oldItem: SearchListData, newItem: SearchListData): Boolean {
        return oldItem == newItem
    }

    override fun getChangeItemPayload(oldItem: SearchListData, newItem: SearchListData): Any? {
        return if (oldItem.isFavorite != newItem.isFavorite) SearchPayLoadType.FAVORITE
        else null
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (viewType) {
            SearchType.IMAGE.ordinal -> {
                ImageViewHolder(
                    ItemSearchImageBinding.inflate(parent.context.layoutInflater, parent, false)
                )
            }
            SearchType.VIDEO.ordinal -> {
                VideoViewHolder(
                    ItemSearchVideoBinding.inflate(parent.context.layoutInflater, parent, false)
                )
            }
            else -> {
                PageViewHolder(
                    ItemSearchPageBinding.inflate(parent.context.layoutInflater, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewDataBinding>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.forEach {
                when (it) {
                    SearchPayLoadType.FAVORITE -> {
                        (holder as? BaseMediaViewHolder<*, *>)?.updateFavorite(adapterList[position].isFavorite)
                    }
                    else -> super.onBindViewHolder(holder, position, payloads)
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
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
        abstract fun updateFavorite(isSelected: Boolean)
    }

    inner class ImageViewHolder(
        binding: ItemSearchImageBinding
    ) : BaseMediaViewHolder<ItemSearchImageBinding, SearchListData.ImageDocumentData>(binding) {

        init {
            binding.btnFavorite.setOnClickListener {
                val data = adapterList[absoluteAdapterPosition]
                val action = if (data.isFavorite) UpdateFavoriteActionType.DELETE else UpdateFavoriteActionType.ADD
                onClick.invoke(action, data)
            }
        }

        override fun initViewHolder(): Unit = with(binding) {
            ivThumbnail.setImage(null)
            val context = binding.root.context
            val thumbWidth = context.getDimensionInt(R.dimen.search_thumbnail_width)
            val height = (data.height * thumbWidth / data.width).takeIf { it <= 300.toPx(context) }
                ?: 300.toPx(context)
            ivThumbnail.layoutParams.height = height
            imageData = data
            btnFavorite.isSelected = data.isFavorite
        }

        override fun updateFavorite(isSelected: Boolean) = with(binding) {
            btnFavorite.isSelected = isSelected
        }
    }

    inner class VideoViewHolder(
        binding: ItemSearchVideoBinding
    ) : BaseMediaViewHolder<ItemSearchVideoBinding, SearchListData.VideoDocumentData>(binding) {

        init {
            binding.btnFavorite.setOnClickListener {
                val data = adapterList[absoluteAdapterPosition]
                val action = if (data.isFavorite) UpdateFavoriteActionType.DELETE else UpdateFavoriteActionType.ADD
                onClick.invoke(action, data)
            }
        }

        override fun initViewHolder(): Unit = with(binding) {
            ivThumbnail.setImage(null)
            val context = binding.root.context
            val thumbWidth = context.getDimensionInt(R.dimen.search_thumbnail_width)
            val height = 78 * thumbWidth / 138
            ivThumbnail.layoutParams.height = height
            videoData = data
            btnFavorite.isSelected = data.isFavorite
        }

        override fun updateFavorite(isSelected: Boolean) = with(binding) {
            btnFavorite.isSelected = isSelected
        }
    }

    inner class PageViewHolder(
        binding: ItemSearchPageBinding
    ) : BaseViewHolder<ItemSearchPageBinding>(binding) {

        init {
            val layoutParams =
                this.itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams
            layoutParams?.isFullSpan = true
        }

        override fun bind(position: Int) {
            val data = adapterList[position] as SearchListData.PageData
            binding.tvPage.text = binding.root.context.getString(R.string.text_page, data.page)
        }
    }
}