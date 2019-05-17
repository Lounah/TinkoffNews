package com.lounah.tinkoffnews.presentation.feed.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lounah.tinkoffnews.presentation.extensions.dpToPx

class NewsFeedDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 12.dpToPx(), 0, 0)
    }
}