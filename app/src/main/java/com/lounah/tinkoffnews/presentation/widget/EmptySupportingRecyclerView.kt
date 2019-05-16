package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lounah.tinkoffnews.presentation.extensions.hide
import com.lounah.tinkoffnews.presentation.extensions.show

class EmptySupportingRecyclerView : RecyclerView {

    var emptyView: View? = null
        set(view) {
            field = view
            checkIfEmpty()
        }

    var onEmptyViewStateChangeListener: (emptyViewVisibility: Int) -> Unit = {}

    private val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    internal fun checkIfEmpty() {
        val emptyView = this.emptyView
        val adapter = this.adapter
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter.itemCount == 0
            if (emptyViewVisible) {
                hide()
                emptyView.show()
            } else {
                show()
                emptyView.hide()
            }
            onEmptyViewStateChangeListener(emptyView.visibility)
        }
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }
}