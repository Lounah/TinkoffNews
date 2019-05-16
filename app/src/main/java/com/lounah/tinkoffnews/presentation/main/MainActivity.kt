package com.lounah.tinkoffnews.presentation.main

import android.os.Bundle
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseActivity
import com.lounah.tinkoffnews.presentation.feed.NewsFeedFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setUpMainFragment()
        }
    }

    private fun setUpMainFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.containerMain, NewsFeedFragment())
                .commit()
    }
}
