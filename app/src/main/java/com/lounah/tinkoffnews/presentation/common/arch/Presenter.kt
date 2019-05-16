package com.lounah.tinkoffnews.presentation.common.arch

interface Presenter<V : MvpView> {

    fun attachView(mvpView: V)

    fun detachView()
}