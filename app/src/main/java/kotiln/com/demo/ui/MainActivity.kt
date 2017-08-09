package kotiln.com.demo.ui

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import com.kotiln.permissionlibrary.PermissionListener
import com.kotiln.permissionlibrary.PermissionUtil
import kotiln.com.baselibrary.tools.dialog.impl.DialogInfaceImpl
import kotiln.com.demo.R
import org.jetbrains.anko.*

/**
 * Created by fulixin on 2017/8/9.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(UI {
            linearLayout {
                orientation=LinearLayout.VERTICAL
                textView {
                    text = "kotlin练习"
                    textSizeDimen = R.dimen.page_text_size
                    textColor = resources.getColor(R.color.Red)
                    gravity = Gravity.CENTER
                    backgroundColor = resources.getColor(R.color.Blue)
                    padding = resources.getDimensionPixelOffset(R.dimen.paddingOrmargin_5dp)
                }.lparams {
                    width = LinearLayout.LayoutParams.WRAP_CONTENT
                    height = LinearLayout.LayoutParams.WRAP_CONTENT
                    margin = resources.getDimensionPixelOffset(R.dimen.paddingOrmargin_5dp)
                }
                button {
                    text = "点击弹窗弹出"
                    onClick {
                        DialogInfaceImpl().showDialog(this@MainActivity, "弹出的弹窗").show()
                    }
                }
            }
        }.view)
        var permisssionUtil: PermissionUtil = PermissionUtil(this@MainActivity)
        permisssionUtil.requestPermissions(Array<String>(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE }, object : PermissionListener {
            override fun onGranted() {
                Toast.makeText(applicationContext, "全部授权成功", Toast.LENGTH_SHORT).show()
            }

            override fun onDenied(deniedPermission: List<String>) {
                Toast.makeText(applicationContext, "onDenied", Toast.LENGTH_SHORT).show()
            }

            override fun onShouldShowRationale(deniedPermission: List<String>) {
                Toast.makeText(applicationContext, "onShouldShowRationale", Toast.LENGTH_SHORT).show()
            }
        })
    }
}