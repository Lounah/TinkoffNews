package com.lounah.tinkoffnews.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val EXTRA_CURRENT_TAB_TYPE = "currentTabType"
private const val EXTRA_TABS_STACK = "tabsStack"
private const val EXTRA_START_TAB = "startTab"

class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var navigationItemsHelper: NavigationItemsHelper

    private val stack: ReorderedLinkedHashSet<String> = ReorderedLinkedHashSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        restoreTabsStack(savedInstanceState)

        setupBottomNavigationView(navigationItemsHelper.navigationItems)

        presenter.onViewCreated(
                intent.extras?.getSerializable(EXTRA_START_TAB) as BottomNavigationItemType?,
                savedInstanceState?.getSerializable(EXTRA_CURRENT_TAB_TYPE) as BottomNavigationItemType?,
                navigationItemsHelper
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        presenter.onNewIntent(intent.extras?.getSerializable(EXTRA_START_TAB) as BottomNavigationItemType?,
                navigationItemsHelper)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(
                EXTRA_CURRENT_TAB_TYPE,
                navigationItemsHelper.mapViewItemId(bottomNavigationMain.selectedItemId).type
        )
        val array = ArrayList(stack.toMutableList())
        outState.putStringArrayList(EXTRA_TABS_STACK, array)
    }

    override fun finishActivity() {
        super.onBackPressed()
    }

    override fun selectBottomNavigationItem(tabId: Int) {
        bottomNavigationMain.selectedItemId = tabId
    }

    override fun onBackPressed() {
        presenter.onBackPressed(
                supportFragmentManager.findFragmentByTag(MAIN_TAB_TYPE.fragmentTag)?.isDetached == false,
                stack,
                navigationItemsHelper
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.containerMain)?.onActivityResult(requestCode, resultCode, data)
    }

    private fun restoreTabsStack(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val stackList = savedInstanceState.getStringArrayList(EXTRA_TABS_STACK)
            if (stackList != null) {
                stack.clear()
                stack.addAll(stackList)
            }
        }
    }

    private fun setupBottomNavigationView(navigationItems: ArrayList<BottomNavigationItem>) {
        for (menuItem in navigationItems) {
            bottomNavigationMain.menu.add(0,
                    menuItem.idRes,
                    0,
                    menuItem.titleRes)
                    .setIcon(menuItem.imageRes)
        }

        val navigationMenuView = bottomNavigationMain.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until navigationMenuView.childCount) {
            val item = navigationMenuView.getChildAt(i)
            val activeLabel = item.findViewById<AppCompatTextView>(R.id.largeLabel)
            if (activeLabel is TextView) {
                activeLabel.setPadding(0, 0, 0, 0)
            }
        }

        bottomNavigationMain.setOnNavigationItemSelectedListener { menuItem ->
            navigationItemsHelper.onNavigationItemSelected(
                    navigationItemsHelper.mapViewItem(menuItem),
                    supportFragmentManager,
                    stack
            )

            true
        }
    }
}
