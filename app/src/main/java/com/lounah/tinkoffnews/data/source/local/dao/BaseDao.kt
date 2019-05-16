package com.lounah.tinkoffnews.data.source.local.dao

import io.reactivex.Completable
import io.reactivex.Single

interface BaseDao<T> {
    fun getAll(): Single<List<T>>
    fun add(item: T): Completable
    fun addAll(items: List<T>): Completable
}