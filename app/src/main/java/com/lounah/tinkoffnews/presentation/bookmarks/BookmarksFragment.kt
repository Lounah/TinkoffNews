package com.lounah.tinkoffnews.presentation.bookmarks

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment
import com.lounah.tinkoffnews.presentation.feed.list.NewsFeedAdapter
import com.lounah.tinkoffnews.presentation.feed.list.StorySummaryViewHolder
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import com.lounah.tinkoffnews.presentation.newsdetails.StoryDetailsActivity
import com.lounah.tinkoffnews.presentation.widget.StoryPreview
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import javax.inject.Inject

private const val VIEW_DATA = 0
private const val VIEW_PROGRESS = 1

private const val REQUEST_ITEM_STATE_CHANGE = 0x0111

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

    override fun onStoryRemovedFromBookmarks(story: StoryViewObject) {
        adapter.removeItem(story)
        showToast(getString(R.string.successfully_removed), null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ITEM_STATE_CHANGE) {
            val recentlyOpenedStoryId = data?.getIntExtra(StoryDetailsActivity.EXTRA_RECENTLY_OPENED_STORY_ID, -1) ?: -1
            val recentlyOpenedStoryIsSavedToBookmarks = data?.getBooleanExtra(StoryDetailsActivity.EXTRA_RECENTLY_OPENED_STORY_IS_SAVED_TO_BOOKMARKS, false) ?: false
            if (recentlyOpenedStoryIsSavedToBookmarks.not()) {
                adapter.removeItemWithId(recentlyOpenedStoryId)
            }
        }
    }


    private fun initRecyclerView() {
        adapter = NewsFeedAdapter(object : NewsFeedAdapter.OnStoryClickedCallback {
            override fun onStoryClicked(story: StoryViewObject) {
                context?.let {
                    startActivityForResult(StoryDetailsActivity.createStartIntent(it, story.id), REQUEST_ITEM_STATE_CHANGE)
                }
            }
        }, object : StoryPreview.OnBookmarkClickedCallback {
            override fun onBookmarkClicked(story: StoryViewObject) {
                presenter.removeStoryFromBookmarks(story)
            }
        })
        recyclerViewBookmarks.adapter = adapter
        recyclerViewBookmarks.layoutManager = LinearLayoutManager(context)
        recyclerViewBookmarks.emptyView = emptyViewBookmarks
    }
}