package com.example.mediasearchingapp.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<B : ViewDataBinding, T> : RecyclerView.Adapter<BaseViewHolder<B>>() {

    protected val adapterList: MutableList<T> get() = asyncListDiffer.currentList

    private val asyncListDiffer by lazy { AsyncListDiffer(this, itemCallback) }

    private val itemCallback by lazy {
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) =
                areItemsSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T) =
                areContentsSame(oldItem, newItem)

            override fun getChangePayload(oldItem: T, newItem: T) =
                getChangeItemPayload(oldItem, newItem)
        }
    }

    abstract fun areItemsSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsSame(oldItem: T, newItem: T): Boolean

    protected open fun getChangeItemPayload(oldItem: T, newItem: T): Any? = null

    protected abstract fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<B>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getBinding(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) = holder.bind(position)

    override fun getItemCount(): Int {
        return adapterList.size
    }

    fun submit(list: List<T>) {
        asyncListDiffer.submitList(ArrayList(list))
    }

}

abstract class BaseViewHolder<out B : ViewDataBinding>(open val binding: B) :
    RecyclerView.ViewHolder(binding.root) {
    open fun bind(position: Int) = Unit
}