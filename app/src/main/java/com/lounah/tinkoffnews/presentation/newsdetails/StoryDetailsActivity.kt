package com.lounah.tinkoffnews.presentation.newsdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.TypedValue
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.presentation.common.BaseActivity
import com.lounah.tinkoffnews.presentation.extensions.fromHtml
import com.lounah.tinkoffnews.presentation.extensions.getDrawableCompat
import kotlinx.android.synthetic.main.activity_story_details.*
import javax.inject.Inject

private const val VIEW_DATA = 0
private const val VIEW_PROGRESS = 1
private const val VIEW_ERROR = 2

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
        textViewStoryDetailsTitle.text = content.title.text
        textViewStoryDate.text = content.getShortFormattedDate()
        textViewStoryContent.text = content.content.fromHtml()
        textViewStoryName.text = content.title.name
    }

    override fun setNormalFontSize() {
        textViewStoryContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_content_normal))
    }

    override fun setMiddleFontSize() {
        textViewStoryContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_content_middle))
    }

    override fun setLargeFontSize() {
        textViewStoryContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_content_large))
    }

    private fun initUI() {
        setUpToolbar()
        buttonChangeFontSize.setOnClickListener {
            presenter.onChangeFontSizeClicked()
        }
        presenter.onCreate(storyId)
    }

    private fun setUpToolbar() {
        toolbarStoryDetails.apply {
            setShouldShowElevation(false)
            setMenuIcon(context.getDrawableCompat(R.drawable.messenger_icons_bookmark_32)!!) {

            }
            setNavigationIcon(context.getDrawableCompat(R.drawable.ic_arrow_back_black_24dp)!!) {
                finish()
            }
        }
    }
}