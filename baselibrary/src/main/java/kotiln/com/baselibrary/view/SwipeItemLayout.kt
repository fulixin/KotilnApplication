package kotiln.com.baselibrary.view

import android.content.res.TypedArray
import android.support.v4.widget.ScrollerCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Interpolator
import android.widget.AbsListView
import android.widget.FrameLayout
import kotiln.com.baselibrary.R

/**
 * Created by fulixin on 2017/8/14.
 */
class SwipeItemLayout (contentView: View, menuView: View, openInterpolator: Interpolator?, closeInterpolator: Interpolator?) : FrameLayout(contentView.context) {
    private var contentView: View? = null
    private var menuView: View? = null
    private var closeInterpolator: Interpolator? = null
    private var openInterpolator: Interpolator? = null

    private var mOpenScroller: ScrollerCompat
    private var mCloseScroller: ScrollerCompat

    private var mBaseX: Int = 0
    private var mDownX: Int = 0

    private val STATE_CLOSE: Int = 0
    private val STATE_OPEN: Int = 0
    private var state: Int = STATE_CLOSE

    private var itemViewId: Int = 0
    private var swipeViewId: Int = 0

//    constructor(contentView: View, menuView: View,openInterpolator: Interpolator?,closeInterpolator: Interpolator?) : super(contentView.context) {
//        this.contentView = contentView
//        this.menuView = menuView
//        this.openInterpolator=openInterpolator
//        this.closeInterpolator=closeInterpolator
//    }

    fun parseAttrs(attrs: AttributeSet) {
        var arr: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeLayoutStyle)
        itemViewId = arr.getResourceId(R.styleable.SwipeLayoutStyle_item_view, 0)
        swipeViewId = arr.getResourceId(R.styleable.SwipeLayoutStyle_swipe_view, 0)
        arr.recycle()
        if (itemViewId != 0) {
            this.contentView = LayoutInflater.from(context).inflate(itemViewId, this, false)
        }
        if (swipeViewId != 0) {
            this.menuView = LayoutInflater.from(context).inflate(swipeViewId, this, false)
        }
    }

    init {
        this.contentView=contentView
        this.menuView=menuView
        layoutParams = AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        if (closeInterpolator != null) {
            mCloseScroller = ScrollerCompat.create(context, closeInterpolator)
        } else {
            mCloseScroller = ScrollerCompat.create(context)
        }

        if (openInterpolator != null) {
            mOpenScroller = ScrollerCompat.create(context, openInterpolator)
        } else {
            mOpenScroller = ScrollerCompat.create(context)
        }

        if (contentView != null) {
            var contentParams: LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            contentView!!.layoutParams = contentParams
            addView(contentView)
        }

        if (menuView != null) {
            menuView!!.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            addView(menuView)
        }
    }

    fun onSwipe(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = event.x.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                var dis: Int = (mDownX - event.x).toInt()
                if (state == STATE_OPEN) {
                    dis += menuView!!.width
                }
                swipe(dis)
            }
            MotionEvent.ACTION_UP -> {
                if ((mDownX - event.x) > (menuView!!.width / 2)) {
                    smoothOpenMenu()
                } else {
                    smoothCloseMenu()
                    return false
                }
            }
        }
        return true
    }

    open fun isOpen(): Boolean {
        return state == STATE_OPEN
    }

    fun swipe(dis: Int) {
        var a: Int
        if (dis > menuView!!.width) {
            a = menuView!!.width
        } else {
            a = dis
        }
        if (a < 0) {
            a = 0
        }
        contentView!!.layout(-a, contentView!!.top, contentView!!.width - a, measuredHeight)
        menuView!!.layout(contentView!!.width - a, menuView!!.top, contentView!!.width + menuView!!.width - a, menuView!!.bottom)
    }

    override fun computeScroll() {
        if (state == STATE_OPEN) {
            if (mOpenScroller.computeScrollOffset()) {
                swipe(mOpenScroller.currX)
                postInvalidate()
            }
        } else {
            if (mCloseScroller.computeScrollOffset()) {
                swipe(mBaseX - mCloseScroller.currX)
                postInvalidate()
            }
        }
    }

    fun smoothCloseMenu() {
        state = STATE_CLOSE
        mBaseX = -contentView!!.left
        println(mBaseX)
        mCloseScroller.startScroll(0, 0, mBaseX, 0, 350)
        postInvalidate()
    }

    fun smoothOpenMenu() {
        state = STATE_OPEN
        mOpenScroller.startScroll(-contentView!!.left, 0,
                menuView!!.width, 0, 350)
        postInvalidate()
    }

    fun closeMenu() {
        if (mCloseScroller.computeScrollOffset()) {
            mCloseScroller.abortAnimation()
        }
        if (state == STATE_OPEN) {
            state = STATE_CLOSE
            swipe(0)
        }
    }

    fun openMenu() {
        if (state == STATE_CLOSE) {
            state = STATE_OPEN
            swipe(menuView!!.width)
        }
    }

    fun getContentView(): View {
        return contentView!!
    }

    fun getMenuView(): View {
        return menuView!!
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        menuView!!.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(
                measuredHeight, View.MeasureSpec.EXACTLY))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        contentView!!.layout(0, 0, measuredWidth,
                contentView!!.measuredHeight)
        menuView!!.layout(measuredWidth, 0,
                measuredWidth + menuView!!.measuredWidth,
                contentView!!.measuredHeight)
        // setMenuHeight(mContentView.getMeasuredHeight());
        // bringChildToFront(mContentView);
    }
}