package com.lounah.tinkoffnews.presentation.common.arch

import com.lounah.tinkoffnews.presentation.extensions.asyncCompletable
import com.lounah.tinkoffnews.presentation.extensions.asyncSingle
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
abstract class BasePresenter<T : MvpView> : Presenter<T> {

    lateinit var commonDisposable: CompositeDisposable

    private var _mvpView: T? = null
    val mvpView: T?
        get() {
            return _mvpView
        }

    override fun attachView(mvpView: T) {
        this._mvpView = mvpView
        commonDisposable = CompositeDisposable()
    }

    override fun detachView() {
        _mvpView = null
        if (::commonDisposable.isInitialized)
            commonDisposable.dispose()
    }

    fun <T> subscribe(single: Single<T>, onNext: (result: T) -> Unit, onError: (error: Throwable) -> Unit): Disposable {
        val disposable = single.compose(asyncSingle())
                .subscribe(onNext, onError)
        commonDisposable.add(disposable)
        return disposable
    }

    fun subscribe(completable: Completable, onNext: Action, onError: Consumer<Throwable>): Disposable {
        val disposable = completable.compose(asyncCompletable())
                .subscribe(onNext, onError)
        commonDisposable.add(disposable)
        return disposable
    }
}