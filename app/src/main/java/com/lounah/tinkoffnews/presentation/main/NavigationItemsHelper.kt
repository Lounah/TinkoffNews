package com.lounah.tinkoffnews.presentation.main

import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.bookmarks.BookmarksFragment
import com.lounah.tinkoffnews.presentation.feed.NewsFeedFragment
import com.lounah.tinkoffnews.presentation.settings.AdvancedOptionsFragment
import java.util.LinkedHashSet
import javax.inject.Inject

val MAIN_TAB_TYPE = BottomNavigationItemType.TAB_FEED

class NavigationItemsHelper
@Inject constructor() {

    val navigationItems: ArrayList<BottomNavigationItem> = createBottomNavigationItems()

    private fun createBottomNavigationItems(): ArrayList<BottomNavigationItem> {
        val menuItems = ArrayList<BottomNavigationItem>()
        menuItems.add(BottomNavigationItem(R.string.bottom_navigation_feed,
                R.drawable.ic_menu_feed,
                View.generateViewId(),
                BottomNavigationItemType.TAB_FEED,
                object : BottomNavigationFragmentFactory {
                    override fun newInstance(): Fragment {
                        return NewsFeedFragment.newInstance()
                    }
                }))
        menuItems.add(BottomNavigationItem(R.string.bottom_navigation_bookmarks,
                R.drawable.fb_ic_book_outline_24,
                View.generateViewId(),
                BottomNavigationItemType.TAB_BOOKMARKS,
                object : BottomNavigationFragmentFactory {
                    override fun newInstance(): Fragment {
                        return BookmarksFragment.newInstance()
                    }
                }))
        menuItems.add(BottomNavigationItem(R.string.bottom_navigation_advanced,
                R.drawable.ic_menu_more_28,
                View.generateViewId(),
                BottomNavigationItemType.TAB_SETTINGS,
                object : BottomNavigationFragmentFactory {
                    override fun newInstance(): Fragment {
                        return AdvancedOptionsFragment.newInstance()
                    }
                }))

        return menuItems
    }

    fun onNavigationItemSelected(
        modelMenuItem: BottomNavigationItem,
        supportFragmentManager: FragmentManager,
        tabsTack: ReorderedLinkedHashSet<String>
    ): Boolean {

        val topFragment = supportFragmentManager.findFragmentById(R.id.containerMain)

        if (topFragment != null && topFragment.tag == modelMenuItem.type.fragmentTag) {
            return true
        }

        val transaction = supportFragmentManager.beginTransaction()

        // hide everything
        for (fragment in supportFragmentManager.fragments) {
            fragment?.let { transaction.detach(it) }
        }

        val tasksFragment = supportFragmentManager.findFragmentByTag(modelMenuItem.type.fragmentTag)
        if (tasksFragment == null) {
            transaction.add(R.id.containerMain,
                    modelMenuItem.fragmentFactory.newInstance(),
                    modelMenuItem.type.fragmentTag)
        } else {
            transaction.attach(tasksFragment)
        }
        transaction.commit()
        tabsTack.add(modelMenuItem.type.fragmentTag)

        return true
    }

    fun mapViewItemId(@IdRes selectedItemId: Int): BottomNavigationItem {
        return navigationItems.first { it.idRes == selectedItemId }
    }

    fun mapViewItem(menuItem: MenuItem): BottomNavigationItem {
        return navigationItems.first { it.idRes == menuItem.itemId }
    }

    fun mapType(itemType: BottomNavigationItemType): BottomNavigationItem {
        return navigationItems.first { it.type == itemType }
    }

    fun mapTag(fragmentTag: String): BottomNavigationItem {
        return navigationItems.first { it.type.fragmentTag == fragmentTag }
    }
}

data class BottomNavigationItem(
    @StringRes val titleRes: Int,
    @DrawableRes val imageRes: Int,
    @IdRes val idRes: Int,
    val type: BottomNavigationItemType,
    val fragmentFactory: BottomNavigationFragmentFactory
)

enum class BottomNavigationItemType(val fragmentTag: String) {
    TAB_FEED(NewsFeedFragment.getFragmentTag()),
    TAB_BOOKMARKS(BookmarksFragment.getFragmentTag()),
    TAB_SETTINGS(AdvancedOptionsFragment.getFragmentTag())
}

interface BottomNavigationFragmentFactory {
    fun newInstance(): Fragment
}

/**
set.add("one");
set.add("two");
set.add("three");
set.add("two");

prints: one, three, two
 */
class ReorderedLinkedHashSet<T> : LinkedHashSet<T>() {
    override fun add(element: T): Boolean {
        if (contains(element)) remove(element)
        return super.add(element)
    }

    fun getLast(): T? {
        var last: T? = null
        for (e in this) last = e
        return last
    }

    fun removeLast() {
        getLast()?.let { remove(it) }
    }
}