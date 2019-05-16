package com.lounah.tinkoffnews.presentation.newsdetails

import com.lounah.tinkoffnews.data.prefs.FontSharedPreferences
import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import com.lounah.tinkoffnews.presentation.common.arch.BasePresenter
import com.lounah.tinkoffnews.presentation.extensions.async
import timber.log.Timber

class StoryDetailsPresenter(
    private val newsFeedInteractor: NewsFeedInteractor,
    private val fontSharedPreferences: FontSharedPreferences
) : BasePresenter<StoryDetailsMvpView>() {

    fun onCreate(storyId: Int) {
        fetchCurrentFontPrefs()
        fetchStoryDetails(storyId)
    }

    fun fetchStoryDetails(storyId: Int) {
        commonDisposable.add(newsFeedInteractor.fetchStoryById(storyId)
                .async()
                .doOnSubscribe {
                    mvpView?.showFullscreenLoading()
                }.subscribe({
                    mvpView?.showContent(it)

                }, {
                    Timber.e(it)
                    mvpView?.showFullscreenError()
                }))
    }

    fun onChangeFontSizeClicked() {
        fontSharedPreferences.applyNewFontPref()
        fetchCurrentFontPrefs()
    }

    private fun fetchCurrentFontPrefs() {
        val fontPrefs = fontSharedPreferences.getCurrentFontPrefs()

        when (fontPrefs.value) {
            FontSharedPreferences.FontPrefs.FONT_SIZE_NORMAL.value -> mvpView?.setNormalFontSize()
            FontSharedPreferences.FontPrefs.FONT_SIZE_MIDDLE.value -> mvpView?.setMiddleFontSize()
            FontSharedPreferences.FontPrefs.FONT_SIZE_LARGE.value -> mvpView?.setLargeFontSize()
            else -> mvpView?.setNormalFontSize()
        }
    }

}