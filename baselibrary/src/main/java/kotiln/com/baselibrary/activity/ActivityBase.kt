package kotiln.com.baselibrary.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotiln.com.baselibrary.tools.note.Attribute
import kotiln.com.baselibrary.tools.note.ViewInits
import org.jetbrains.anko.onClick

/**
 * Created by fulixin on 2017/8/10.
 */
abstract class ActivityBase : AppCompatActivity() {
    lateinit var attributes: MutableList<Attribute>
    /**
     * 返回布局文件
     */
    abstract fun getLayout(): View

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 单击事件处理
     */
    abstract fun initOnClick(v: View?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        init();
    }

    private fun init() {
        attributes = ArrayList()
        autoInjectAllField()
        initData()
    }

    /**
     * 根据view布局id返回view视图
     */
    public fun viewIdToView(mContext:Context,viewId: Int): View {
        return LayoutInflater.from(mContext).inflate(viewId, null);
    }

    /**
     * 遍历属性，初始化控件
     * 准备工作，写代码效率提高了，但是运行时耗时长了，因为要遍历所有属性
     */
    private fun autoInjectAllField() {
        try {
            val clazz = this.javaClass
            val fields = clazz.declaredFields// 获得Activity中声明的字段
            for (field in fields) {
                // 查看这个字段是否有我们自定义的注解类标志的
                if (field.isAnnotationPresent(ViewInits::class.java)) {
                    val id = field.getAnnotation(ViewInits::class.java).viewId
                    if (id > 0) {
                        field.isAccessible = true
                        field.set(this@ActivityBase, this.findViewById(id))// 给我们要找的字段设置值
                        val isNull = field.getAnnotation(ViewInits::class.java).isNull
                        if (isNull) {
                            attributes.add(Attribute(field.get(this@ActivityBase) as View, field.type, field.getAnnotation(ViewInits::class.java).message))
                        }
                        val isOnclick = field.getAnnotation(ViewInits::class.java).onClick
                        if (isOnclick) {
                            (field.get(this@ActivityBase) as View).onClick {
                                view ->
                                initOnClick(view)
                            }
                        }
                    }
                }
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    public fun isNull(): Boolean {
        for (attribute in attributes) {
            if (attribute.type!!.name.equals("android.widget.TextView")) {
                if ((attribute.view as TextView).text.isEmpty()) {
                    Toast.makeText(this@ActivityBase, attribute.message, Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            if (attribute.type!!.name.equals("android.widget.EditText")) {
                if ((attribute.view as EditText).text.isEmpty()) {
                    Toast.makeText(this@ActivityBase, attribute.message, Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }
}