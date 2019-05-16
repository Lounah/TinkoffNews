package com.lounah.tinkoffnews.presentation.newsdetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.TypedValue
import android.webkit.WebSettings
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.presentation.common.BaseActivity
import com.lounah.tinkoffnews.presentation.extensions.fromHtml
import com.lounah.tinkoffnews.presentation.extensions.getDrawableCompat
import com.lounah.tinkoffnews.presentation.extensions.spToPx
import kotlinx.android.synthetic.main.activity_story_details.*
import javax.inject.Inject

private const val VIEW_DATA = 0
private const val VIEW_PROGRESS = 1
private const val VIEW_ERROR = 2

private const val CONTENT_SIZE_ZOOM_NORMAL = 100
private const val CONTENT_SIZE_ZOOM_MEDIUM = 150
private const val CONTENT_SIZE_ZOOM_LARGE = 200

class StoryDetailsActivity : BaseActivity(), StoryDetailsMvpView {

    companion object {
        private const val EXTRA_STORY_ID = "extra_story_id"
        fun createStartIntent(context: Context, storyId: Int): Intent = Intent(context, StoryDetailsActivity::class.java).apply {
            putExtra(EXTRA_STORY_ID, storyId)
        }
    }

    @Inject
    lateinit var presenter: StoryDetailsPresenter

    private var storyId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.activity_transition_rtl_enter, R.anim.activity_transition_rtl_exit)
        super.onCreate(savedInstanceState)
        storyId = intent.getIntExtra(EXTRA_STORY_ID, -1)
        setContentView(R.layout.activity_story_details)
        presenter.attachView(this)
        initUI()
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

    override fun showContent(content: StoryDetails) {
        if (viewFlipperStoryDetails.displayedChild != VIEW_DATA) {
            viewFlipperStoryDetails.displayedChild = VIEW_DATA
        }
        textViewStoryDetailsTitle.text = content.title.text.fromHtml()
        textViewStoryDate.text = content.getShortFormattedDate()
        webView.loadData(content.content, "text/html; charset=UTF-8", null)
        textViewStoryName.text = content.title.name
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
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_transition_stub, R.anim.activity_transition_ltr_exit)
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
            setMenuIcon(context.getDrawableCompat(R.drawable.ic_bookmark_white)!!) {

            }
            setNavigationIcon(context.getDrawableCompat(R.drawable.ic_arrow_back_black_24dp)!!) {
                onBackPressed()
            }
            setOnClickListener {
                scrollViewStoryDetailsContent.smoothScrollTo(0, 0)
            }
        }
    }
}