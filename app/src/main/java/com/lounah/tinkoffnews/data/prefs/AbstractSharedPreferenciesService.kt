package com.lounah.tinkoffnews.data.prefs

import android.content.Context
import android.content.SharedPreferences

abstract class AbstractSharedPreferencesService(context: Context, name: String) {
    protected val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
}
