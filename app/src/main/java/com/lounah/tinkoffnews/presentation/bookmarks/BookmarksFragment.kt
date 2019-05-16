package com.lounah.tinkoffnews.presentation.bookmarks

import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment

class BookmarksFragment : BaseFragment() {
    override val TAG = "bookmarks_fragment"

    override val layoutRes = R.layout.fragment_bookmarks

    companion object {
        fun newInstance(): BookmarksFragment = BookmarksFragment()
    }

    override fun initUI() {

    }
}