package kotiln.com.baselibrary.tools.note

/**
 * @param viewId 控件id
 * @param onClick 是否有单击事件
 * @param isNull 是否做非空判断
 * @param message 为空的提示内容
 * Created by fulixin on 2017/8/10.
 */
annotation class ViewInits(
        val viewId: Int,
        val onClick: Boolean,
        val isNull: Boolean,
        val message: String
)