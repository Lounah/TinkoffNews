package com.lounah.tinkoffnews.presentation.feed.list

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject

private const val DELAY_LOADING_SPINNER_MS = 150L
private const val RECYCLER_VIEW_BOTTOM_SCROLL_DIRECTION = 1

abstract class NewsFeedOnScrollListener(
    private val adapter: NewsFeedAdapter
) : RecyclerView.OnScrollListener() {

    private val uiHandler = Handler(Looper.getMainLooper())

    private var loadingBefore = false
    private var lastElement: StoryViewObject? = null
    private var previousBefore: StoryViewObject? = lastElement

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        lastElement = adapter.getEarliestItem()

        if (dy != 0 && recyclerView.canScrollVertically(RECYCLER_VIEW_BOTTOM_SCROLL_DIRECTION).not() && loadingBefore.not()) { // up
            onLoadBefore(lastElement)
            loadingBefore = true
        }
    }

    fun onLoadedBefore() {
        uiHandler.postDelayed({
            if (previousBefore != lastElement) {
                previousBefore = lastElement
            }
            loadingBefore = false
        }, DELAY_LOADING_SPINNER_MS)
    }

    fun releaseLoadingBeforeState() {
        loadingBefore = false
    }

    fun isLoadingBefore() = loadingBefore

    abstract fun onLoadBefore(before: StoryViewObject?)
}