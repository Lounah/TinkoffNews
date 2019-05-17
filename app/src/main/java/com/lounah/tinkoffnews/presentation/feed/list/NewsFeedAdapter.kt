package com.lounah.tinkoffnews.presentation.feed.list

import android.view.ViewGroup
import com.lounah.tinkoffnews.presentation.common.recycler.BaseAdapter
import com.lounah.tinkoffnews.presentation.common.recycler.BaseViewHolder
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject

class NewsFeedAdapter(
    private val onStoryClickedCallback: OnStoryClickedCallback,
    private val onBookmarkClickedCallback: StorySummaryViewHolder.OnBookmarkClickedCallback
) : BaseAdapter<StoryViewObject>() {

    interface OnStoryClickedCallback {
        fun onStoryClicked(story: StoryViewObject)
    }

    private var isLoading: Boolean = false

    override fun getItemViewType(position: Int): Int {
        return if (position == getLoadingPosition() && isLoading) {
            NewsFeedViewHolders.LOADING.type
        } else NewsFeedViewHolders.FEED_ITEM.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<StoryViewObject> {
        return when (viewType) {
            NewsFeedViewHolders.FEED_ITEM.type -> {
                val viewHolder = StorySummaryViewHolder(parent, onBookMarkClicked = onBookmarkClickedCallback)
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
            NewsFeedViewHolders.LOADING.type -> { }
            NewsFeedViewHolders.FEED_ITEM.type -> {
                (holder as StorySummaryViewHolder).bind(items[position])
            }
        }
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
        }
    }

    fun setItemWithIdIsSavedToBookmarks(itemId: Int, isSavedToBookmarks: Boolean) {
        val item = items.firstOrNull { it.id == itemId }
        item?.let {
            val index = items.indexOf(it)
            items[index].apply { isBookmarked = isSavedToBookmarks }
            notifyItemChanged(index)
        }
    }

    fun removeItemWithId(id: Int) {
        val item = items.firstOrNull { it.id == id }
        item?.let {
            val index = items.indexOf(it)
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun getEarliestItem() = items.lastOrNull()

    private fun getLoadingPosition() = items.size
}