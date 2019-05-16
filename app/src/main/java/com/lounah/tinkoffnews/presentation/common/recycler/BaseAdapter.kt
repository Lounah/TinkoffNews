package com.lounah.tinkoffnews.presentation.common.recycler

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    val items = mutableListOf<T>()

    override fun getItemCount() = items.size

    fun addItem(item: T, position: Int? = null) {
        val prevSize = items.size
        if (items.contains(item).not()) {
            if (position == null) {
                items.add(item)
                notifyItemInserted(items.indexOf(item))
            } else {
                items.add(position, item)
                notifyItemInserted(position)
            }
        }
    }

    fun addItems(items: List<T>, startPosition: Int? = null) {
        val oldSize = this.items.size
        val uniqueItems = items.filter { this.items.contains(it).not() }
        if (uniqueItems.isNotEmpty()) {
            if (startPosition == null) {
                this.items.addAll(uniqueItems)
                notifyItemRangeInserted(oldSize, uniqueItems.size)
            } else {
                this.items.addAll(startPosition, uniqueItems)
                notifyItemRangeInserted(startPosition, uniqueItems.size)
            }
        }
    }

    fun removeItem(item: T) {
        val index = items.indexOf(item)
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateItem(item: T) {
        val index = items.indexOf(item)
        if (index != -1) {
            items[index] = item
            notifyItemChanged(index)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}