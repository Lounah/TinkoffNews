package com.lounah.tinkoffnews.presentation.feed

import androidx.recyclerview.widget.LinearLayoutManager
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedAdapter
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedOnScrollListener
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import kotlinx.android.synthetic.main.fragment_news_feed.*

class NewsFeedFragment : BaseFragment() {
    override val TAG = "news_feed"
    override val layoutRes = R.layout.fragment_news_feed

    private lateinit var newsFeedAdapter: NewsFeedAdapter
    private lateinit var newsFeedListOnScrollListener: NewsFeedOnScrollListener

    override fun initUI() {
        setUpToolbar()
        setUpRecyclerView()
    }

    private fun setUpToolbar() {
        toolbarNewsFeed.apply {
            hideMenuIcon()
            hideNavigationIcon()
            setTitle(getString(R.string.feed))
        }
    }

    private fun setUpRecyclerView() {
        newsFeedAdapter = NewsFeedAdapter(object : NewsFeedAdapter.OnStoryClickedCallback {
            override fun onStoryClicked(story: StoryViewObject) {

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
                    newsFeedAdapter.showLoading()
                    // TODO: additional logic
                }
            }
            addOnScrollListener(newsFeedListOnScrollListener)
        }

    }
}