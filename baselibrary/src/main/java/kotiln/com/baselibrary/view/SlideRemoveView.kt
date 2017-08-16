package kotiln.com.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotiln.com.baselibrary.inface.ICallBackInface

/**
 * 作废
 * Created by fulixin on 2017/8/14.
 */
class SlideRemoveView constructor(context: Context) : ViewGroup(context) {
    private var contentView: View? = null
    private var deleteView: View? = null
    private var deleteViewWidth: Int = 0
    private var mDragHelper: MyViewDragHelper? = null
    lateinit var icallback: ICallBackInface

    constructor(context: Context, attrs: AttributeSet) : this(context) {
        // 实例花ViewDragHelper 第一个参数是ViewGroup容器，第二个是回调
        mDragHelper = MyViewDragHelper.create(this@SlideRemoveView, MyCallBack(), icallback)
    }

    constructor(context: Context, icallback: ICallBackInface, contentView: View, deleteView: View) : this(context) {
        // 实例花ViewDragHelper 第一个参数是ViewGroup容器，第二个是回调
        mDragHelper = MyViewDragHelper.create(this@SlideRemoveView, MyCallBack(), icallback)
        addView(contentView)
        addView(deleteView)
        onFinishInflate()
    }

    private inner class MyCallBack : MyViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            // 返回true表示处理事件
            // return false;
            // 表示接收处理contentView和deleteView的事件
            return child === contentView || child === deleteView
        }

        // 这个方法是处理水平方法向的事件
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            // child表示你要处理容器中哪一个child left是返回的child的左上角的坐标控制水平移动
            if (child === contentView) {
                // 表示处理contentView这个控件的事件了
                if (left > 0) {
                    return 0 // contentView的左上角大于了0就返回0 表示不可以再向右拖动
                } else if (left < -deleteView!!.measuredWidth) {
                    return -deleteView!!.measuredWidth
                }
            }

            if (child === deleteView) {
                // 处理deleteView控件的事件了
                if (left > contentView!!.measuredWidth) {
                    return contentView!!.measuredWidth
                } else if (left < contentView!!.measuredWidth - deleteView!!.measuredWidth)
                    return contentView!!.measuredWidth - deleteView!!.measuredWidth
            }
            return left
        }

        // 控件位置改变的时候调用
        override fun onViewPositionChanged(changedView: View, left: Int, top: Int,
                                           dx: Int, dy: Int) {
            // left就是控件改变的左上角的位置
            if (changedView === contentView) {
                // contentView位置改变了 ，那就动态去计算deleteView的位置并重新放置
                val deleteLeft = contentView!!.measuredWidth + left
                val deleteTop = 0
                val deleteRight = deleteLeft + deleteView!!.measuredWidth
                val deleteBottom = deleteView!!.measuredHeight

                deleteView!!.layout(deleteLeft, deleteTop, deleteRight,
                        deleteBottom)
            } else if (changedView === deleteView) {
                // deleteView位置改变了 ，那就动态去计算contentView的位置并重新放置
                val contentViewLeft = -(contentView!!.measuredWidth - left)
                val contentViewRight = contentView!!.measuredWidth + contentViewLeft
                val contentViewTop = 0
                val contentViewBottom = contentView!!.measuredHeight

                contentView!!.layout(contentViewLeft, contentViewTop,
                        contentViewRight, contentViewBottom)
            }

        }

        // 滑动松开的时候调用
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            // 获取到contentView的左上标
            val contentViewLeft = contentView!!.left
            if (contentViewLeft < -deleteViewWidth / 2) {
                // 小于了deleteView的一半 完全显示删除
                showDeleteView()
            } else {
                // 完全不显示deleteView
                noShowDeleteView()
            }
        }

        /**
         * 完全显示deleteView
         */
        private fun showDeleteView() {
            val contentViewLeft = -deleteView!!.measuredWidth
            val contentViewRight = contentViewLeft + contentView!!.measuredWidth
            val contentViewTop = 0
            val contentViewBottom = contentView!!.measuredHeight

            contentView!!.layout(contentViewLeft, contentViewTop,
                    contentViewRight, contentViewBottom)
            // 放deleteView的位置
            val deleteLeft = contentView!!.measuredWidth + contentViewLeft
            val deleteTop = 0
            val deleteRight = deleteLeft + deleteView!!.measuredWidth
            val deleteBottom = deleteView!!.measuredHeight

            deleteView!!.layout(deleteLeft, deleteTop, deleteRight, deleteBottom)
        }

    }

    // 关系到拖动都会实现onTouchEvent方法
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper!!.processTouchEvent(event)
        // 返回ture 事件自己给消掉
        return true
    }

    protected override fun onFinishInflate() {
        // 布局加载完成的时候调用
        // 在布局加载完成获取容器中两个子孩子
        contentView = getChildAt(0)
        deleteView = getChildAt(1)
        // 获取deleteView的宽度
        deleteViewWidth = deleteView!!.layoutParams.width
    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 测量两个子孩子的大小 contentView的宽高和父容器一样 所以可以用父容器的这个两个参数来测量
        contentView!!.measure(widthMeasureSpec, heightMeasureSpec)
        // deleteView的宽和父容器不一样，所以带模式精确测量
        val deleteWdith = View.MeasureSpec.makeMeasureSpec(deleteViewWidth,
                View.MeasureSpec.EXACTLY)
        deleteView!!.measure(deleteWdith, heightMeasureSpec)

        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec),
                View.MeasureSpec.getSize(heightMeasureSpec))

    }

    protected override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        noShowDeleteView()
    }

    fun noShowDeleteView() {
        // 放contentView的位置
        val dex = 0
        val contentViewLeft = 0 + dex
        val contentViewRight = contentView!!.measuredWidth + dex
        val contentViewTop = 0
        val contentViewBottom = contentView!!.measuredHeight

        contentView!!.layout(contentViewLeft, contentViewTop, contentViewRight,
                contentViewBottom)
        // 放deleteView的位置
        val deleteLeft = contentView!!.measuredWidth + dex
        val deleteTop = 0
        val deleteRight = contentView!!.measuredWidth
        +deleteView!!.measuredWidth + dex
        val deleteBottom = deleteView!!.measuredHeight

        deleteView!!.layout(deleteLeft, deleteTop, deleteRight, deleteBottom)
    }
}
