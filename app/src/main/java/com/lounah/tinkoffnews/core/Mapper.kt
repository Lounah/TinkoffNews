package com.lounah.tinkoffnews.core

interface Mapper<From, To> {
    fun map(from: From): To

    fun map(list: List<From>): List<To> = list.map { map(it) }
}