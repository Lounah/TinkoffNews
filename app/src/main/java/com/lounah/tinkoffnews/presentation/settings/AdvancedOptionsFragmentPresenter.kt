package com.lounah.tinkoffnews.presentation.settings

import com.lounah.tinkoffnews.presentation.common.arch.BasePresenter

class AdvancedOptionsFragmentPresenter : BasePresenter<AdvancedOptionsView>() {

    fun onReviewLeft() {
        mvpView?.showReviewLeftSuccess()
    }

    fun licensesClicked() {
        mvpView?.showLicensesDialog()
    }
}