package com.lounah.tinkoffnews.presentation.newsdetails

import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.presentation.common.arch.MvpView

interface StoryDetailsMvpView : MvpView {
    fun showFullscreenLoading()
    fun hideFullscreenLoading()
    fun showFullscreenError()
    fun setNormalFontSize()
    fun setMiddleFontSize()
    fun setLargeFontSize()
    fun showContent(content: StoryDetails)
}