package com.lounah.tinkoffnews.presentation.feed

import androidx.recyclerview.widget.LinearLayoutManager
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedAdapter
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedOnScrollListener
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import com.lounah.tinkoffnews.presentation.newsdetails.StoryDetailsActivity
import kotlinx.android.synthetic.main.fragment_news_feed.*
import javax.inject.Inject
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.lounah.tinkoffnews.presentation.feed.list.StorySummaryViewHolder
import kotlin.properties.Delegates


private const val VIEW_DATA = 0
private const val VIEW_PROGRESS = 1
private const val VIEW_ERROR = 2

private const val EXTRA_RECYCLER_VIEW_SCROLL_POSITION = "EXTRA_RECYCLER_VIEW_SCROLL_POSITION"

private const val SCROLLED_ITEMS_TO_SHOW_SCROLL_TO_TOP_FAB_THRESHOLD = 5

class NewsFeedFragment : BaseFragment(), NewsFeedView {
    override val layoutRes = R.layout.fragment_news_feed

    companion object {
        fun getFragmentTag() = "news_feed"
        fun newInstance(): NewsFeedFragment = NewsFeedFragment()
    }

    @Inject
    lateinit var presenter: NewsFeedPresenter

    private lateinit var newsFeedAdapter: NewsFeedAdapter
    private lateinit var newsFeedListOnScrollListener: NewsFeedOnScrollListener

    private var savedScrollPosition = 0

    private var scrolledPostsCount: Int by Delegates.observable(0) { _, _, newValue ->
        if (newValue == 0) {
            fabNewsFeedScrollToTop.hide()
        } else {
            if (newValue > SCROLLED_ITEMS_TO_SHOW_SCROLL_TO_TOP_FAB_THRESHOLD) {
                fabNewsFeedScrollToTop.show()
            }
        }
    }

    override fun initUI() {
        setUpToolbar()
        setUpRecyclerView()
        setUpSwipeRefreshLayout()
        fabNewsFeedScrollToTop.setOnClickListener {
            recyclerViewNewsFeed?.post {
                recyclerViewNewsFeed.scrollToPosition(0)
                appBarLayoutNewsFeed.setExpanded(true, true)
            }
        }
        presenter.attachView(this)
        presenter.onCreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onPause() {
        super.onPause()
        savedScrollPosition = (recyclerViewNewsFeed.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }

    override fun onResume() {
        super.onResume()
        recyclerViewNewsFeed.postDelayed({
            (recyclerViewNewsFeed.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(savedScrollPosition, 0)
        }, 100)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        recyclerViewNewsFeed?.let {
            outState.putInt(EXTRA_RECYCLER_VIEW_SCROLL_POSITION, (it.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedScrollPosition = savedInstanceState?.getInt(EXTRA_RECYCLER_VIEW_SCROLL_POSITION) ?: savedScrollPosition
    }

    override fun showErrorToast() {
        showToast(R.string.error_loading_data, gravity = null)
    }

    override fun showFullscreenError() {
        if (viewFlipperNewsFeed.displayedChild != VIEW_ERROR) {
            viewFlipperNewsFeed.displayedChild = VIEW_ERROR
        }
        errorViewNewsFeed.setErrorButton(R.string.error_state_default_retry) {
            presenter.onRetryClicked()
        }
    }

    override fun showFullscreenLoading() {
        if (viewFlipperNewsFeed.displayedChild != VIEW_PROGRESS) {
            viewFlipperNewsFeed.displayedChild = VIEW_PROGRESS
        }
    }

    override fun showPagingLoading() {
        recyclerViewNewsFeed.post {
            newsFeedAdapter.showLoading()
        }
    }

    override fun showData(feed: List<StoryViewObject>) {
        if (viewFlipperNewsFeed.displayedChild != VIEW_DATA) {
            viewFlipperNewsFeed.displayedChild = VIEW_DATA
        }
        newsFeedListOnScrollListener.onLoadedBefore()
        newsFeedListOnScrollListener.releaseLoadingBeforeState()
        newsFeedAdapter.addItems(feed)
    }

    override fun hideSwipeRefresh() {
        swipeRefreshNewsFeed.isRefreshing = false
    }

    private fun setUpToolbar() {
        toolbarNewsFeed.apply {
            hideMenuIcon()
            hideNavigationIcon()
            setShouldShowElevation(false)
            setTitle(getString(R.string.feed))
            setOnClickListener {
                recyclerViewNewsFeed.post {
                    recyclerViewNewsFeed.smoothScrollToPosition(0)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        newsFeedAdapter = NewsFeedAdapter(object : NewsFeedAdapter.OnStoryClickedCallback {
            override fun onStoryClicked(story: StoryViewObject) {
                context?.let {
                    startActivity(StoryDetailsActivity.createStartIntent(it, story.id))
                }
            }
        }, object : StorySummaryViewHolder.OnBookmarkClickedCallback {
            override fun onBookmarkClicked(storyId: Int) {

            }
        })
        recyclerViewNewsFeed.apply {
            adapter = newsFeedAdapter
            layoutManager = LinearLayoutManager(this@NewsFeedFragment.context).apply {
                initialPrefetchItemCount = 3
                isItemPrefetchEnabled = true
            }
            newsFeedListOnScrollListener = object : NewsFeedOnScrollListener(newsFeedAdapter) {
                override fun onLoadBefore(before: StoryViewObject?) {
                    presenter.fetchNewsFeed(forceRefresh = false, initialLoading = false)
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    savedScrollPosition = (recyclerViewNewsFeed.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()
                    scrolledPostsCount = savedScrollPosition
                    if (recyclerView.canScrollVertically(-1).not()) {
                        fabNewsFeedScrollToTop.hide()
                    }
                }

            }
            addOnScrollListener(newsFeedListOnScrollListener)
        }

    }

    private fun setUpSwipeRefreshLayout() {
        swipeRefreshNewsFeed.setOnRefreshListener {
            presenter.onPullToRefreshTriggered()
        }
    }
}