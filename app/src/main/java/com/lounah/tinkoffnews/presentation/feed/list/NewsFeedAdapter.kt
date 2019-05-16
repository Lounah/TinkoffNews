package com.lounah.tinkoffnews.presentation.feed.list

import android.view.ViewGroup
import com.lounah.tinkoffnews.presentation.common.recycler.BaseAdapter
import com.lounah.tinkoffnews.presentation.common.recycler.BaseViewHolder
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject

private const val LOADING_POSITION = 0

class NewsFeedAdapter(private val onStoryClickedCallback: OnStoryClickedCallback) : BaseAdapter<StoryViewObject>() {

    interface OnStoryClickedCallback {
        fun onStoryClicked(story: StoryViewObject)
    }

    private var isLoading: Boolean = false

    override fun getItemViewType(position: Int): Int {
        return if (position == LOADING_POSITION && isLoading) {
            NewsFeedViewHolders.LOADING.type
        } else NewsFeedViewHolders.FEED_ITEM.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<StoryViewObject> {
        return when(viewType) {
            NewsFeedViewHolders.FEED_ITEM.type -> {
                val viewHolder = StorySummaryViewHolder(parent)
                viewHolder.itemView.setOnClickListener {
                    onStoryClickedCallback.onStoryClicked(items[viewHolder.adapterPosition])
                }
                viewHolder
            }
            NewsFeedViewHolders.LOADING.type -> LoadingViewHolder(parent)
            else -> LoadingViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<StoryViewObject>, position: Int) {
        val viewType = getItemViewType(position)

        when (viewType) {
            NewsFeedViewHolders.LOADING.type -> {
                (holder as LoadingViewHolder).bind(null)
            }
            NewsFeedViewHolders.FEED_ITEM.type -> {
                (holder as StorySummaryViewHolder).bind(items[position])
            }
        }
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            notifyItemInserted(LOADING_POSITION)
        }
    }

    fun hideLoading() {
        if (isLoading) {
            isLoading = false
            notifyItemRemoved(LOADING_POSITION)
        }
    }

    fun removeItemById(itemId: Int) {
        val item = items.firstOrNull { it.id == itemId }
        item?.let {
            val index = items.indexOf(it)
            items.remove(it)
            notifyItemRemoved(index)
        }
    }

    fun getEarliestItem() = items.lastOrNull()

}