package kotiln.com.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.widget.ListView

/**
 * Created by fulixin on 2017/8/14.
 */
class SwipeListView : ListView {
    private val TOUCH_STATE_NONE: Int = 0
    private val TOUCH_STATE_X: Int = 1
    private val TOUCH_STATE_Y: Int = 2

    private var MAX_Y: Int = 5
    private var MAX_X: Int = 3
    private var mDownX: Float = 0.toFloat()
    private var mDownY: Float = 0.toFloat()
    private var mTouchState: Int = 0
    private var mTouchPosition = 0
    private var mTouchView: SwipeItemLayout? = null

    var mCloseInterpolator: Interpolator? = null
    var mOpenInterpolator: Interpolator? = null

    var isCanSwipe: Boolean = true


    constructor(mContext: Context, attrs: AttributeSet, defStyle: Int) : super(mContext, attrs, defStyle) {}

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs) {}

    constructor(mContext: Context) : super(mContext) {}

    init {
        this.MAX_X = dp2px(MAX_X)
        this.MAX_Y = dp2px(MAX_Y)
        mTouchState = TOUCH_STATE_NONE
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action != MotionEvent.ACTION_DOWN && mTouchView == null) {
            return super.onTouchEvent(ev)
        }
        var action: Int = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                var oldPos: Int = mTouchPosition
                mDownX = ev.x
                mDownY = ev.y
                mTouchState = TOUCH_STATE_NONE
                mTouchPosition = pointToPosition(ev.x.toInt(), ev.y.toInt())
                if (mTouchPosition == oldPos && mTouchView != null && mTouchView!!.isOpen()) {
                    mTouchState = TOUCH_STATE_X
                    if (isCanSwipe) {
                        mTouchView!!.onSwipe(ev)
                    }
                    return true
                }
                var view: View = getChildAt(mTouchPosition - firstVisiblePosition)
                if (mTouchView != null && mTouchView!!.isOpen()) {
                    mTouchView!!.smoothCloseMenu()
                    mTouchView = null
                    return super.onTouchEvent(ev)
                }
                if (view is SwipeItemLayout) {
                    mTouchView = view as SwipeItemLayout
                } else {
                    if (view is ViewGroup) {
                        for (i in 0..view.childCount) {
                            var childView: View = view.getChildAt(i)
                            if (childView is SwipeItemLayout) {
                                mTouchView = childView as SwipeItemLayout
                            }
                        }
                    }
                }
                if (mTouchView != null) {
                    if (isCanSwipe) {
                        mTouchView!!.onSwipe(ev)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                var dy: Float = Math.abs(ev.y - mDownY)
                var dx: Float = Math.abs(ev.x - mDownX)
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        if (isCanSwipe) {
                            mTouchView!!.onSwipe(ev)
                        }
                    }
                    selector.state = intArrayOf(0)
                    ev.action = MotionEvent.ACTION_CANCEL
                    super.onTouchEvent(ev)
                    return true
                } else if (mTouchState == TOUCH_STATE_NONE) {
                    if (Math.abs(dy) > MAX_Y) {
                        mTouchState = TOUCH_STATE_Y
                    } else if (dx > MAX_X) {
                        mTouchState = TOUCH_STATE_X
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        if (isCanSwipe) {
                            mTouchView!!.onSwipe(ev)
                        }
                        if (!mTouchView!!.isOpen()) {
                            mTouchPosition = -1
                            mTouchView = null
                        }
                    }
                    ev.action = MotionEvent.ACTION_CANCEL
                    super.onTouchEvent(ev)
                    return true
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    open fun smoothOpenMenu(position: Int) {
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            var view: View = getChildAt(position - firstVisiblePosition)
            if (view is SwipeItemLayout) {
                mTouchPosition = position
                if (mTouchView != null && mTouchView!!.isOpen()) {
                    mTouchView!!.smoothCloseMenu()
                }
                mTouchView = view
                mTouchView!!.smoothOpenMenu()
            }
        }
    }

    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }
}