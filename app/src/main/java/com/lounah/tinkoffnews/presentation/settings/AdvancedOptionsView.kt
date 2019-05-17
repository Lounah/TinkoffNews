package com.lounah.tinkoffnews.presentation.settings

import com.lounah.tinkoffnews.presentation.common.arch.MvpView

interface AdvancedOptionsView : MvpView {
    fun showLicensesDialog()
    fun showAbout()
    fun showReviewLeftSuccess()
}