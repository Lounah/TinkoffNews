package com.lounah.tinkoffnews.presentation.feed

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedAdapter
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedOnScrollListener
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import com.lounah.tinkoffnews.presentation.newsdetails.StoryDetailsActivity
import kotlinx.android.synthetic.main.fragment_news_feed.*
import javax.inject.Inject

private const val VIEW_DATA = 0
private const val VIEW_PROGRESS = 1
private const val VIEW_ERROR = 2

class NewsFeedFragment : BaseFragment(), NewsFeedView {
    override val TAG = "news_feed"
    override val layoutRes = R.layout.fragment_news_feed

    companion object {
        fun newInstance(): NewsFeedFragment = NewsFeedFragment()
    }

    @Inject lateinit var presenter: NewsFeedPresenter

    private lateinit var newsFeedAdapter: NewsFeedAdapter
    private lateinit var newsFeedListOnScrollListener: NewsFeedOnScrollListener

    override fun initUI() {
        setUpToolbar()
        setUpRecyclerView()
        setUpSwipeRefreshLayout()
        presenter.attachView(this)
        presenter.onCreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun showErrorToast() {
        showToast(R.string.error_loading_data, gravity = null)
    }

    override fun showFullscreenError() {
        if (viewFlipperNewsFeed.displayedChild != VIEW_ERROR) {
            viewFlipperNewsFeed.displayedChild = VIEW_ERROR
        }
        toolbarNewsFeed.setShouldShowElevation(true)
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
//        newsFeedAdapter.showLoading()
    }

    override fun showData(feed: List<StoryViewObject>) {
        if (viewFlipperNewsFeed.displayedChild != VIEW_DATA) {
            viewFlipperNewsFeed.displayedChild = VIEW_DATA
        }
//        newsFeedAdapter.hideLoading()
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
        }
    }

    private fun setUpRecyclerView() {
        newsFeedAdapter = NewsFeedAdapter(object : NewsFeedAdapter.OnStoryClickedCallback {
            override fun onStoryClicked(story: StoryViewObject) {
                context?.let {
                    startActivity(StoryDetailsActivity.createStartIntent(it, story.id))
                }
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
//                    newsFeedAdapter.showLoading()
                    // TODO: additional logic
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

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