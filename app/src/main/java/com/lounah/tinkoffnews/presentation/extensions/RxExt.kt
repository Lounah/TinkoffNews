package com.lounah.tinkoffnews.presentation.extensions

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> asyncSingle(): SingleTransformer<T, T> {
    return SingleTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun asyncCompletable(): CompletableTransformer {
    return CompletableTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> asyncFlowable(): FlowableTransformer<T, T> {
    return FlowableTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> asyncObservable(): ObservableTransformer<T, T> {
    return ObservableTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun Disposable?.isDisposed(): Boolean {
    return !(this != null && !this.isDisposed)
}

fun <T> Flowable<T>.async(): Flowable<T> = compose(asyncFlowable())
fun <T> Single<T>.async(): Single<T> = compose(asyncSingle())
fun Completable.async(): Completable = compose(asyncCompletable())
fun <T> Observable<T>.async(): Observable<T> = compose(asyncObservable())