package com.lounah.tinkoffnews.presentation.newsdetails

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.data.source.local.entity.StoryDetailsEntity
import com.lounah.tinkoffnews.presentation.common.BaseActivity
import com.lounah.tinkoffnews.presentation.extensions.fromHtml
import com.lounah.tinkoffnews.presentation.extensions.getDrawableCompat
import kotlinx.android.synthetic.main.activity_story_details.*
import javax.inject.Inject

/**
 *  ViewFlipper's view positions
 */
private const val VIEW_DATA = 0
private const val VIEW_PROGRESS = 1
private const val VIEW_ERROR = 2

private const val CONTENT_SIZE_ZOOM_NORMAL = 100
private const val CONTENT_SIZE_ZOOM_MEDIUM = 150
private const val CONTENT_SIZE_ZOOM_LARGE = 200

class StoryDetailsActivity : BaseActivity(), StoryDetailsMvpView {

    companion object {
        /**
         *  These two guys stand for a recently opened story (obvious).
         *  Cuz i've used SQLLite here, idk how to make it 100% reactive, so i can not trigger
         *  an update on recyclerView item, when it was changed.
         *
         *  We can add a story to bookmarks here, but NewsFeedFragment will not know anything about this,
         *  so we start a [StoryDetailsActivity] for result and pass two parameters back (is story saved & story's id)
         */
        const val EXTRA_RECENTLY_OPENED_STORY_ID = "EXTRA_RECENTLY_OPENED_STORY_ID"
        const val EXTRA_RECENTLY_OPENED_STORY_IS_SAVED_TO_BOOKMARKS = "EXTRA_RECENTLY_OPENED_STORY_IS_SAVED_TO_BOOKMARKS"
        private const val EXTRA_STORY_ID = "extra_story_id"
        fun createStartIntent(context: Context, storyId: Int): Intent = Intent(context, StoryDetailsActivity::class.java).apply {
            putExtra(EXTRA_STORY_ID, storyId)
        }
    }

    @Inject
    lateinit var presenter: StoryDetailsPresenter

    private var storyId: Int = -1
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.activity_transition_rtl_enter, R.anim.activity_transition_rtl_exit)
        super.onCreate(savedInstanceState)
        storyId = intent.getIntExtra(EXTRA_STORY_ID, -1)
        setContentView(R.layout.activity_story_details)
        presenter.attachView(this)
        initUI()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showFullscreenLoading() {
        if (viewFlipperStoryDetails.displayedChild != VIEW_PROGRESS) {
            viewFlipperStoryDetails.displayedChild = VIEW_PROGRESS
        }
    }

    override fun showFullscreenError() {
        if (viewFlipperStoryDetails.displayedChild != VIEW_ERROR) {
            viewFlipperStoryDetails.displayedChild = VIEW_ERROR
        }
        errorViewStoryDetails.setErrorButton(R.string.error_state_default_retry) {
            presenter.fetchStoryDetails(storyId)
        }
    }

    override fun hideFullscreenLoading() {
        if (viewFlipperStoryDetails.displayedChild != VIEW_DATA) {
            viewFlipperStoryDetails.displayedChild = VIEW_DATA
        }
    }

    override fun showContent(content: StoryDetailsEntity) {
        if (viewFlipperStoryDetails.displayedChild != VIEW_DATA) {
            viewFlipperStoryDetails.displayedChild = VIEW_DATA
        }
        textViewStoryDetailsTitle.text = content.title.fromHtml()
        textViewStoryDate.text = content.getShortFormattedDate()

        /**
         *  I know that webView rendering can be slow and tricky, but news format is so diverse,
         *  so just using .fromHtml() isn't enough.
         *  So i decided to use webView here in a case to support every story
         */
        webView.loadData(content.content, "text/html; charset=UTF-8", null)

        textViewStoryName.text = content.name

        isBookmarked = content.isBookmarked

        if (content.isBookmarked) {
            toolbarStoryDetails.setMenuIcon(getDrawableCompat(R.drawable.ic_bookmark_filled)!!) {
                presenter.removeFromBookmarks(content.id)
            }
        } else {
            toolbarStoryDetails.setMenuIcon(getDrawableCompat(R.drawable.ic_bookmark_white)!!) {
                presenter.addToBookmarks(content.id)
            }
        }
    }

    override fun setNormalFontSize() {
        webView.settings.textZoom = CONTENT_SIZE_ZOOM_NORMAL
    }

    override fun setMiddleFontSize() {
        webView.settings.textZoom = CONTENT_SIZE_ZOOM_MEDIUM
    }

    override fun setLargeFontSize() {
        webView.settings.textZoom = CONTENT_SIZE_ZOOM_LARGE
    }

    override fun onBackPressed() {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_RECENTLY_OPENED_STORY_ID, storyId)
            putExtra(EXTRA_RECENTLY_OPENED_STORY_IS_SAVED_TO_BOOKMARKS, isBookmarked)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        overridePendingTransition(R.anim.activity_transition_stub, R.anim.activity_transition_ltr_exit)
    }

    override fun onAddedToBookmarks(storyId: Int) {
        isBookmarked = true
        toolbarStoryDetails.setMenuIcon(getDrawableCompat(R.drawable.ic_bookmark_filled)!!) {
            presenter.removeFromBookmarks(storyId)
        }
    }

    override fun onRemovedFromBookmarks(storyId: Int) {
        isBookmarked = false
        toolbarStoryDetails.setMenuIcon(getDrawableCompat(R.drawable.ic_bookmark_white)!!) {
            presenter.addToBookmarks(storyId)
        }
    }

    private fun initUI() {
        setUpToolbar()
        initContentWebView()
        buttonChangeFontSize.setOnClickListener {
            presenter.onChangeFontSizeClicked()
        }
        presenter.onCreate(storyId)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initContentWebView() {
        webView.settings.apply {
            cacheMode = WebSettings.LOAD_NO_CACHE
            setAppCacheEnabled(false)
            blockNetworkImage = true
            loadsImagesAutomatically = false
            blockNetworkLoads = false
            javaScriptEnabled = true
            setGeolocationEnabled(false)
            setNeedInitialFocus(false)
        }
    }

    private fun setUpToolbar() {
        toolbarStoryDetails.apply {
            setShouldShowElevation(false)
            setMenuIcon(context.getDrawableCompat(R.drawable.ic_bookmark_white)!!) {}
            setNavigationIcon(context.getDrawableCompat(R.drawable.ic_arrow_back_black_24dp)!!) {
                onBackPressed()
            }
            setOnClickListener {
                scrollViewStoryDetailsContent.smoothScrollTo(0, 0) // to the top
            }
        }
    }
}