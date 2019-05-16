package com.lounah.tinkoffnews.presentation.main

import com.lounah.tinkoffnews.presentation.common.arch.BasePresenter
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
) : BasePresenter<MainMvpView>() {

    fun onViewCreated(
        startTab: BottomNavigationItemType?,
        selectedTabType: BottomNavigationItemType?,
        navigationItemsHelper: NavigationItemsHelper
    ) {
        when {
            selectedTabType != null -> mvpView?.selectBottomNavigationItem(navigationItemsHelper.mapType(selectedTabType).idRes)
            startTab != null -> mvpView?.selectBottomNavigationItem(navigationItemsHelper.mapType(startTab).idRes)
            else -> mvpView?.selectBottomNavigationItem(navigationItemsHelper.mapType(BottomNavigationItemType.TAB_BOOKMARKS).idRes)
        }
    }

    fun onBackPressed(
        isMainTabAttached: Boolean,
        tabsStack: ReorderedLinkedHashSet<String>,
        navigationItemsHelper: NavigationItemsHelper
    ) {
        if (isMainTabAttached) {
            mvpView?.finishActivity()
        } else {
            tabsStack.removeLast()
            val lastItem = tabsStack.getLast()
            if (lastItem != null) {
                mvpView?.selectBottomNavigationItem(navigationItemsHelper.mapTag(lastItem).idRes)
            } else {
                mvpView?.selectBottomNavigationItem(navigationItemsHelper.mapTag(MAIN_TAB_TYPE.fragmentTag).idRes)
            }
        }
    }

    fun onNewIntent(
        bottomNavigationItemType: BottomNavigationItemType?,
        navigationItemsHelper: NavigationItemsHelper
    ) {
        if (bottomNavigationItemType != null) {
            mvpView?.selectBottomNavigationItem(navigationItemsHelper.mapType(bottomNavigationItemType).idRes)
        }
    }
}
