package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.extensions.hide
import com.lounah.tinkoffnews.presentation.extensions.invisible
import com.lounah.tinkoffnews.presentation.extensions.show
import kotlinx.android.synthetic.main.view_state_error.view.*


class ErrorView : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_state_error, this, true)
        gravity = Gravity.CENTER
        orientation = LinearLayout.VERTICAL
        val padding = context.resources.getDimensionPixelSize(R.dimen.default_padding)
        setPadding(padding, padding, padding, padding)
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.ErrorView,
                    0, 0
            )
            try {
                imageError.setImageResource(
                        a.getResourceId(
                                R.styleable.ErrorView_errorImageSrc,
                                R.drawable.placeholder_not_found_56
                        )
                )
                textError.setText(
                        a.getResourceId(
                                R.styleable.ErrorView_errorMessage,
                                R.string.error_state_default_state
                        )
                )
            } finally {
                a.recycle()
            }
        }
    }

    fun setErrorMessage(@StringRes message: Int) {
        textError.setText(message)
    }

    fun setErrorImage(@DrawableRes image: Int) {
        imageError.visibility = View.VISIBLE
        imageError.setImageResource(image)
    }

    fun hideErrorImage() {
        imageError.hide()
    }

    fun setErrorButton(@StringRes message: Int, onClickListener: () -> (Unit)) {
        buttonRetryError.show()
        buttonRetryError.setText(message)
        buttonRetryError.setOnClickListener { onClickListener.invoke() }
    }

    fun hideErrorButton() {
        buttonRetryError.invisible()
    }
}