package com.lounah.tinkoffnews.data.prefs

import android.content.Context
import javax.inject.Inject

const val KEY_CURRENT_FONT_PREF = "key_font_pref"

class FontSharedPreferences @Inject constructor(context: Context)
    : AbstractSharedPreferencesService(context, "font") {

    enum class FontPrefs(val value: Int) {
        FONT_SIZE_NORMAL(0x0001), FONT_SIZE_MIDDLE(0x0002), FONT_SIZE_LARGE(0x0003)
    }

    fun getCurrentFontPrefs(): FontPrefs {
        val fontPref = sharedPreferences.getInt(KEY_CURRENT_FONT_PREF, FontPrefs.FONT_SIZE_NORMAL.value)

        return when(fontPref) {
            FontPrefs.FONT_SIZE_NORMAL.value -> FontPrefs.FONT_SIZE_NORMAL
            FontPrefs.FONT_SIZE_MIDDLE.value -> FontPrefs.FONT_SIZE_MIDDLE
            FontPrefs.FONT_SIZE_LARGE.value -> FontPrefs.FONT_SIZE_LARGE
            else -> FontPrefs.FONT_SIZE_NORMAL
        }
    }

    fun applyNewFontPref(): FontPrefs {
        val currentPrefs = getCurrentFontPrefs()

        return when(currentPrefs.value) {
            FontPrefs.FONT_SIZE_NORMAL.value -> {
                setNewFontSize(FontPrefs.FONT_SIZE_MIDDLE)
                FontPrefs.FONT_SIZE_MIDDLE
            }
            FontPrefs.FONT_SIZE_MIDDLE.value -> {
                setNewFontSize(FontPrefs.FONT_SIZE_LARGE)
                FontPrefs.FONT_SIZE_LARGE
            }
            FontPrefs.FONT_SIZE_LARGE.value -> {
                setNewFontSize(FontPrefs.FONT_SIZE_NORMAL)
                FontPrefs.FONT_SIZE_NORMAL
            }
            else -> FontPrefs.FONT_SIZE_NORMAL
        }
    }

    private fun setNewFontSize(fontPref: FontPrefs) {
        sharedPreferences.edit()
                .putInt(KEY_CURRENT_FONT_PREF, fontPref.value)
                .apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
