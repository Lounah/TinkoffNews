package com.lounah.tinkoffnews.presentation.widget

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.Window
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.extensions.hide
import com.lounah.tinkoffnews.presentation.extensions.show
import kotlinx.android.synthetic.main.view_dialog.*

class MaterialDialog(context: Context) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.view_dialog)
    }

    class Builder(private val context: Context) {

        private var backgroundColor: Int? = null
        private var title: String? = null
        private var message: Int? = null
        private var stringMessage: String? = null
        private var icon: Int? = null
        private var positiveButtonText: Int? = null
        private var positiveButtonListener: View.OnClickListener? = null
        private var negativeButtonText: Int? = null
        private var negativeButtonListener: View.OnClickListener? = null
        private var isCancelable = true

        fun setBackgroundColor(@ColorRes color: Int): Builder {
            this.backgroundColor = color
            return this
        }

        fun setTitle(@StringRes title: Int): Builder {
            this.title = context.getString(title)
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(@StringRes message: Int): Builder {
            this.message = message
            return this
        }

        fun setStringMessage(stringMessage: String): Builder {
            this.stringMessage = stringMessage
            return this
        }

        fun setIcon(@DrawableRes icon: Int): Builder {
            this.icon = icon
            return this
        }

        fun setPositiveButton(@StringRes message: Int, onClickListener: View.OnClickListener): Builder {
            this.positiveButtonText = message
            positiveButtonListener = onClickListener
            return this
        }

        fun setNegativeButton(@StringRes message: Int, onClickListener: View.OnClickListener): Builder {
            this.negativeButtonText = message
            negativeButtonListener = onClickListener
            return this
        }

        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        fun create(): MaterialDialog {
            val materialDialog = MaterialDialog(context)
            apply(materialDialog)
            return materialDialog
        }

        fun show(): MaterialDialog {
            val materialDialog = create()
            materialDialog.show()
            return materialDialog
        }

        private fun apply(materialDialog: MaterialDialog) {
            val drawable = GradientDrawable()
            drawable.cornerRadius = context.resources.getDimensionPixelSize(R.dimen.dialog_corner_radius).toFloat()
            val bgColor = backgroundColor ?: R.color.white
            drawable.setColor(ContextCompat.getColor(context, bgColor))
            materialDialog.window?.setBackgroundDrawable(drawable)

            title?.let {
                materialDialog.textViewDialogTitle.show()
                materialDialog.textViewDialogTitle.setText(it)
            } ?: run {
                materialDialog.textViewDialogTitle.hide()
            }

            message?.let {
                materialDialog.textViewDialogMessage.show()
                materialDialog.textViewDialogMessage.setText(it)
            } ?: run {
                stringMessage?.let {
                    materialDialog.textViewDialogMessage.show()
                    materialDialog.textViewDialogMessage.text = it
                } ?: run {
                    materialDialog.textViewDialogMessage.hide()
                }
            }

            icon?.let {
                materialDialog.imageViewDialog.show()
                materialDialog.imageViewDialog.setImageResource(it)
            } ?: run {
                materialDialog.imageViewDialog.hide()
            }

            materialDialog.setCancelable(isCancelable)

            positiveButtonListener?.let { onClickListener ->
                materialDialog.buttonPositive.show()
                materialDialog.buttonPositive.setText(positiveButtonText ?: android.R.string.ok)
                materialDialog.buttonPositive.setOnClickListener {
                    onClickListener.onClick(it)
                    materialDialog.dismiss()
                }
            } ?: run {
                materialDialog.buttonPositive.hide()
            }

            negativeButtonListener?.let { onClickListener ->
                materialDialog.buttonNegative.show()
                materialDialog.buttonNegative.setText(negativeButtonText ?: android.R.string.cancel)
                materialDialog.buttonNegative.setOnClickListener {
                    onClickListener.onClick(it)
                    materialDialog.dismiss()
                }
            } ?: run {
                materialDialog.buttonNegative.hide()
            }
        }
    }
}