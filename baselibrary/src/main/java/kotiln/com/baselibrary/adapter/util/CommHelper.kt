package kotiln.com.baselibrary.adapter.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by fulixin on 2017/8/10.
 */
class CommHelper constructor(mContext: Context, parent: ViewGroup?, layoutId: Int) {
    private lateinit var converView: View

    init {
        converView = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
        converView.tag = this
    }

    companion object {
        fun getCommHelper(mContext: Context, converView: View?, parent: ViewGroup?, layoutId: Int): CommHelper {
            if (converView != null) {
                return converView!!.tag as CommHelper
            }
            return CommHelper(mContext, parent, layoutId)
        }
    }

    fun getConverView(): View {
        return converView
    }
}