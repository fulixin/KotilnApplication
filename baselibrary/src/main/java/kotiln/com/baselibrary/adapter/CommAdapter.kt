package kotiln.com.baselibrary.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotiln.com.baselibrary.adapter.util.CommHelper

/**
 * Created by fulixin on 2017/8/10.
 */
public abstract class CommAdapter<T> constructor(mContext: Context, datas: MutableList<T>, layoutId: Int) : BaseAdapter() {
    lateinit var mContext: Context
    lateinit var datas: MutableList<T>
    var layoutId: Int

    init {
        this.mContext = mContext
        this.datas = datas
        this.layoutId = layoutId
    }

    abstract fun initView(viewHelper: CommHelper, item: T, position: Int)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var commHelper: CommHelper = initCommHelper(convertView, parent)
        initView(commHelper, datas[position], position)
        return commHelper.getConverView()
    }

    override fun getItem(position: Int): T {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return datas.size
    }

    private fun initCommHelper(converView: View?, parent: ViewGroup?): CommHelper {
        return CommHelper.getCommHelper(mContext, converView, parent, layoutId)
    }
}