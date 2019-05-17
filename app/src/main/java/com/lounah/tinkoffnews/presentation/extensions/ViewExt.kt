package com.lounah.tinkoffnews.presentation.extensions

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup.MarginLayoutParams


fun Int.dpToPx(): Int {
    val density = Resources.getSystem().displayMetrics.density
    return Math.round(this * density)
}

fun View.layoutView(left: Int, top: Int, width: Int, height: Int) {
    val margins = this.layoutParams as MarginLayoutParams
    val leftWithMargins = left + margins.leftMargin
    val topWithMargins = top + margins.topMargin

    this.layout(leftWithMargins, topWithMargins,
            leftWithMargins + width, topWithMargins + height)
}

fun View.getWidthWithMargins(): Int {
    val lp = this.layoutParams as MarginLayoutParams
    return this.width + lp.leftMargin + lp.rightMargin
}

fun View.getHeightWithMargins(): Int {
    val lp = this.layoutParams as MarginLayoutParams
    return this.measuredHeight + lp.topMargin + lp.bottomMargin
}

fun View.getMeasuredWidthWithMargins(): Int {
    val lp = this.layoutParams as MarginLayoutParams
    return this.measuredWidth + lp.leftMargin + lp.rightMargin
}

fun View.getMeasuredHeightWithMargins(): Int {
    val lp = this.layoutParams as MarginLayoutParams
    return this.measuredHeight + lp.topMargin + lp.bottomMargin
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}