package kotiln.com.demo.ui

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotiln.com.baselibrary.activity.ActivityBase
import kotiln.com.baselibrary.tools.note.ViewInits
import kotiln.com.demo.R

/**
 * Created by fulixin on 2017/8/10.
 */
class MainActivity1 : ActivityBase() {
    @field:ViewInits(R.id.id_activity_main_textview_show, false, false, "")
    lateinit var id_textview_show: TextView

    @field:ViewInits(R.id.id_activity_main_edittext_edit, false, true, "不能为空的数据")
    lateinit var id_edittext_edit: EditText

    @field:ViewInits(R.id.id_activity_main_button_btn, true, false, "")
    lateinit var id_button_btn: Button

    override fun getLayout(): View {
        return viewIdToView(R.layout.activity_main)
    }

    override fun initData() {
        id_textview_show.text = "这是一个测试内容"
        id_button_btn.text = "点击我把"
    }

    override fun initOnClick(v: View?) {
        when (v!!.id) {
            R.id.id_activity_main_button_btn ->
                if (isNull()) {
                    Toast.makeText(this@MainActivity1, "按要求完成了", Toast.LENGTH_SHORT).show()
                }
        }
    }
}