package com.lounah.tinkoffnews.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lounah.tinkoffnews.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    companion object {
        const val TAG = "settings_fragment"

        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
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