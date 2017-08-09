package kotiln.com.baselibrary.tools.dialog

import android.app.Dialog
import android.content.Context

/**
 * 弹窗
 * Created by fulixin on 2017/7/18.
 */
interface IDialogInface {
    /**
     * 提示框，点击空白处关闭
     */
    fun showDialog(mContext: Context, str: String): Dialog
}