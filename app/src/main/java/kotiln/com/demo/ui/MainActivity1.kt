package kotiln.com.demo.ui

import android.view.View
import android.widget.*
import kotiln.com.baselibrary.activity.ActivityBase
import kotiln.com.baselibrary.adapter.CommAdapter
import kotiln.com.baselibrary.adapter.util.impl.CommHelperImpl
import kotiln.com.baselibrary.tools.note.ViewInits
import kotiln.com.demo.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by fulixin on 2017/8/10.
 */
class MainActivity1 : ActivityBase() {
    @field:ViewInits(R.id.id_activity_main_textview_show, false, false, "")
    lateinit var id_textview_show: TextView

//    @field:ViewInits(R.id.id_activity_main_edittext_edit, false, true, "不能为空的数据")
//    lateinit var id_edittext_edit: EditText

    @field:ViewInits(R.id.id_activity_main_button_btn, true, false, "")
    lateinit var id_button_btn: Button

    @field:ViewInits(R.id.id_activity_main_listview_lv, false, false, "")
    lateinit var id_listview_lv: ListView

    override fun getLayout(): View {
        return viewIdToView(this@MainActivity1,R.layout.activity_main)
    }

    override fun initData() {
        id_activity_main_edittext_edit.hint="随便输入吧"
        id_textview_show.text = "这是一个测试内容"
        id_button_btn.text = "点击我把"

        var list: MutableList<String> = ArrayList()
        for (i in 0..5) {
            list.add("数据" + i)
        }
        id_listview_lv.adapter = object : CommAdapter<String>(this@MainActivity1, list, R.layout.activity_main_item, R.layout.activity_main_item_delete) {
            override fun initView(viewHelper: CommHelperImpl, item: String, position: Int) {
                viewHelper.setTextView(R.id.id_activity_main_item_textview_show, item, onClick = View.OnClickListener {
                    Toast.makeText(this@MainActivity1, "单击了" + position, Toast.LENGTH_SHORT).show()
                })
            }

        }
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
