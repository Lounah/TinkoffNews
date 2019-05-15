package com.lounah.tinkoffnews.presentation.common.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(
    parent: ViewGroup,
    layoutRes: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)), LayoutContainer {

    override val containerView = itemView

    abstract fun bind(obj: T)
}