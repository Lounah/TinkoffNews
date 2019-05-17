package com.lounah.tinkoffnews.presentation.main

import androidx.annotation.IdRes
import com.lounah.tinkoffnews.presentation.common.arch.MvpView

interface MainMvpView : MvpView {
    fun finishActivity()
    fun selectBottomNavigationItem(@IdRes tabId: Int)
}