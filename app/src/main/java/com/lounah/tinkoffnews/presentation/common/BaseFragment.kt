package com.lounah.tinkoffnews.presentation.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    fun showToast(text: String, @IntRange(from = 0, to = 4) gravity: Int?) {
        context?.let { context ->
            Toast.makeText(context, text, Toast.LENGTH_SHORT).apply {
                if (gravity != null)
                    this.setGravity(gravity, 0, 0)
            }.show()
        }
    }

    fun showToast(@StringRes text: Int, @IntRange(from = 0, to = 4) gravity: Int?) {
        context?.let { context ->
            Toast.makeText(context, text, Toast.LENGTH_SHORT).apply {
                if (gravity != null)
                    this.setGravity(gravity, 0, 0)
            }.show()
        }
    }

    abstract val TAG: String
    abstract val layoutRes: Int
    abstract fun initUI()
}