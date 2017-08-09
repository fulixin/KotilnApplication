package kotiln.com.baselibrary.tools.dialog.impl

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import kotiln.com.baselibrary.R
import kotiln.com.baselibrary.tools.dialog.IDialogInface
import org.jetbrains.anko.*

/**
 * Created by fulixin on 2017/7/18.
 */
class DialogInfaceImpl : IDialogInface {
    override fun showDialog(mContext: Context, str: String): Dialog {
        var dialog: Dialog = Dialog(mContext)
        var viewUI = mContext.UI {
            verticalLayout {
                textView {
//                    backgroundResource = R.drawable.dialog_bg
                    gravity = Gravity.CENTER
                    textColor = resources.getColor(R.color.Red)
                    text = str
                }
            }
        }
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(viewUI.view)
        return dialog
    }
}