package kotiln.com.baselibrary.adapter.util

import android.view.View

/**
 * Created by fulixin on 2017/8/10.
 */
interface ICommHelperInface {
    fun setTextView(viewId: Int, str: String)
    fun setTextView(viewId: Int, str: String, onClick: View.OnClickListener?)
    fun setTextView(viewId: Int, str: String, onLongClick: View.OnLongClickListener?)
    fun setTextView(viewId: Int, str: String, onClick: View.OnClickListener?, onLongClick: View.OnLongClickListener?)
}