package com.lounah.tinkoffnews.presentation.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import com.lounah.tinkoffnews.BuildConfig
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.BaseFragment
import com.lounah.tinkoffnews.presentation.widget.MaterialDialog
import de.psdev.licensesdialog.LicensesDialog
import kotlinx.android.synthetic.main.fragment_advancedoptions.*
import timber.log.Timber
import javax.inject.Inject

class AdvancedOptionsFragment : BaseFragment(), AdvancedOptionsView {
    override val layoutRes = R.layout.fragment_advancedoptions

    companion object {
        private const val REQUEST_CODE_SEND_WITHDRAWAL = 100
        private const val REQUEST_CODE_PICK_MAIL_APP = 101
        fun getFragmentTag() = "settings_fragment"
        fun newInstance(): AdvancedOptionsFragment = AdvancedOptionsFragment()
    }

    @Inject lateinit var presenter: AdvancedOptionsFragmentPresenter

    override fun initUI() {
        setUpToolbar()
        textViewLicenses.setOnClickListener {
            presenter.licensesClicked()
        }
        textViewSendFeedback.setOnClickListener {
            sendFeedback()
        }
        textViewSettingsAbout.setOnClickListener {
            showAbout()
        }
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_PICK_MAIL_APP -> {
                val appName = data?.component?.flattenToShortString()
                appName?.let {
                    startActivityForResult(data, REQUEST_CODE_SEND_WITHDRAWAL)
                }
            }
            REQUEST_CODE_SEND_WITHDRAWAL -> {
                presenter.onReviewLeft()
            }
        }
    }


    override fun showLicensesDialog() {
        LicensesDialog.Builder(context)
                .setNotices(R.raw.notices)
                .build()
                .show()
    }

    override fun showAbout() {
        context?.let {
            MaterialDialog.Builder(it)
                    .setTitle(R.string.app_name)
                    .setStringMessage(resources.getString(R.string.dialog_about_description, BuildConfig.VERSION_NAME))
                    .setPositiveButton(R.string.dialog_ok, View.OnClickListener { })
                    .show()
        }
    }

    override fun showReviewLeftSuccess() {
        context?.let {
            MaterialDialog.Builder(it)
                    .setTitle(R.string.feedback_left_success_title)
                    .setMessage(R.string.feedback_left_success_title_message)
                    .setPositiveButton(R.string.dialog_ok, View.OnClickListener { })
                    .show()
        }
    }

    private fun sendFeedback() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        val email = StringBuilder().append("mailto:").append(getString(R.string.feedback_email)).toString()
        emailIntent.data = Uri.parse(email)

        val pickActivityIntent = Intent(Intent.ACTION_PICK_ACTIVITY).apply {
            putExtra(Intent.EXTRA_TITLE, resources.getString(R.string.feedback_email_app_picker))
            putExtra(Intent.EXTRA_INTENT, emailIntent)
        }

        try {
            startActivityForResult(pickActivityIntent, REQUEST_CODE_PICK_MAIL_APP)
        } catch (ex: ActivityNotFoundException) {
            Timber.e(ex)
        }
    }

    private fun setUpToolbar() {
        toolbarSettings.apply {
            setTitle(getString(R.string.advanced))
            setShouldShowElevation(true)
            setShouldShowNavigationIcon(false)
        }
    }
}