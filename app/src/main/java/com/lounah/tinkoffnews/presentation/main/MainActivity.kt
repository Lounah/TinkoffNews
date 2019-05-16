package com.lounah.tinkoffnews.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseActivity
import com.lounah.tinkoffnews.presentation.feed.NewsFeedFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    companion object {

        private const val EXTRA_CURRENT_TAB_TYPE = "currentTabType"
        private const val EXTRA_TABS_STACK = "tabsStack"
        private const val EXTRA_START_TAB = "startTab"
    }

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var navigationItemsHelper: NavigationItemsHelper

    /**
     * В данном активити реализован мехнизм сохранения переходов по табам.
     * Первый таб является конечным, и сбрасывает стек переходов.
     * Чтобы после поворота стек сохранялся, эта переменная сохраняется.
     * Эта переменная хранит тэги фрагментов, которые добавлялись в фрагмент менеджер.
     * Если фрагмент был добавлен в фрагмент менеджер, то он не удаляется оттуда во время жизни этого активити. Используется
     * механизм attach() и detach() что соответсвет поведению фрагментов, если бы их добавляли в бекстек фрагментов
     * (т.е. уничтожается только интерфейс при детаче)
     */
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
            // add(int groupId, int itemId, int order, int titleRes)
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

//
//    private fun setUpMainFragment() {
//        supportFragmentManager.beginTransaction()
//                .add(R.id.containerMain, NewsFeedFragment())
//                .commit()
//    }
}
