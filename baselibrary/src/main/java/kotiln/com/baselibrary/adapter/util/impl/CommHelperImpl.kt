package kotiln.com.baselibrary.adapter.util.impl

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotiln.com.baselibrary.adapter.util.ICommHelperInface
import kotiln.com.baselibrary.view.SwipeItemLayout
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick

/**
 * Created by fulixin on 2017/8/10.
 */
class CommHelperImpl constructor(mContext: Context, parent: ViewGroup?, layoutId: Int, isDelete: Boolean, deleteLayoutId: Int) : ICommHelperInface {
    override fun setTextView(viewId: Int, str: String, onLongClick: View.OnLongClickListener?) {
        setTextView(viewId, str, null, onLongClick)
    }

    override fun setTextView(viewId: Int, str: String) {
        setTextView(viewId, str, null, null)
    }

    override fun setTextView(viewId: Int, str: String, onClick: View.OnClickListener?) {
        setTextView(viewId, str, onClick, null)
    }

    override fun setTextView(viewId: Int, str: String, onClick: View.OnClickListener?, onLongClick: View.OnLongClickListener?) {
        var textview: TextView = (converView.findViewById(viewId) as TextView)
        textview.text = str
        if (onClick != null) {
            textview.onClick {
                view ->
                onClick!!.onClick(view)
            }
        }
        if (onLongClick != null) {
            textview.onLongClick {
                view ->
                onLongClick!!.onLongClick(view)
            }
        }
    }

    private lateinit var converView: View

    init {
        if (isDelete) {
            var view1: View = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
            var view2: View = LayoutInflater.from(mContext).inflate(deleteLayoutId, parent, false)
            converView = SwipeItemLayout(view1, view2, null, null)
            converView.tag = this
        } else {
            converView = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
            converView.tag = this
        }
    }

    companion object {
        fun getCommHelper(mContext: Context, converView: View?, parent: ViewGroup?, layoutId: Int, isDelete: Boolean, deleteLayoutId: Int): CommHelperImpl {
            if (converView != null) {
                return converView!!.tag as CommHelperImpl
            }
            return CommHelperImpl(mContext, parent, layoutId, isDelete, deleteLayoutId)
        }
    }

    fun getConverView(): View {
        return converView
    }
}