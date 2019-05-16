package com.lounah.tinkoffnews.presentation.settings

import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {
    override val layoutRes = R.layout.fragment_settings

    companion object {
        fun getFragmentTag() = "settings_fragment"
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun initUI() {
        setUpToolbar()
        textViewLicenses.setOnClickListener {

        }
        textViewSendFeedback.setOnClickListener {

        }
        textViewSettingsAbout.setOnClickListener {

        }
    }

    private fun setUpToolbar() {
        toolbarSettings.apply {
            setTitle(getString(R.string.settings))
            setShouldShowElevation(false)
            setShouldShowNavigationIcon(false)
        }
    }
}