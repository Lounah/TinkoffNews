package com.lounah.tinkoffnews.presentation.bookmarks

import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedAdapter
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import com.lounah.tinkoffnews.presentation.newsdetails.StoryDetailsActivity
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import javax.inject.Inject

private const val VIEW_DATA = 0
private const val VIEW_PROGRESS = 1

class BookmarksFragment : BaseFragment(), BookmarksFragmentView {
    override val layoutRes = R.layout.fragment_bookmarks

    companion object {
        fun getFragmentTag() = "bookmarks_fragment"
        fun newInstance(): BookmarksFragment = BookmarksFragment()
    }

    @Inject lateinit var presenter: BookmarksFragmentPresenter

    private lateinit var adapter: NewsFeedAdapter

    override fun initUI() {
        toolbarBookmarks.apply {
            setShouldShowElevation(false)
            setTitle(getString(R.string.bottom_navigation_bookmarks))
        }
        initRecyclerView()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showFullscreenLoading() {
        if (viewFlipperBookmarks.displayedChild != VIEW_PROGRESS) {
            viewFlipperBookmarks.displayedChild = VIEW_PROGRESS
        }
    }

    override fun showBookmarkedNews(news: List<StoryViewObject>) {
        if (viewFlipperBookmarks.displayedChild != VIEW_DATA) {
            viewFlipperBookmarks.displayedChild = VIEW_DATA
        }
        adapter.addItems(news)
    }

    override fun removeStoryFromBookmarks(storyId: Int) {
        adapter.removeItemById(storyId)
        showToast(getString(R.string.successfully_removed), null)
    }

    private fun initRecyclerView() {
        adapter = NewsFeedAdapter(object : NewsFeedAdapter.OnStoryClickedCallback {
            override fun onStoryClicked(story: StoryViewObject) {
                context?.let {
                    startActivity(StoryDetailsActivity.createStartIntent(it, story.id))
                }
            }
        })
        recyclerViewBookmarks.emptyView = emptyViewBookmarks
    }
}