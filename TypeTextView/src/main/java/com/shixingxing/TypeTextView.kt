package com.shixingxing

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.lang.ref.WeakReference
import java.util.*

/**
 * 类描述:打字机效果自定义类
 */
open class TypeTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        /**
         * 默认显示下一个支付时间间隔
         */
        private const val TYPE_TIME_DELAY = 100L

    }

    interface OnTypeViewListener {
        fun onTypeStart()
        fun onTypeOver()
    }

    var mOnTypeViewListener: OnTypeViewListener? = null
    var mShowTextString: String? = null

    /**
     * 动画时间间隔
     */
    private var mTypeTimeDelay: Long = TYPE_TIME_DELAY

    public fun setTypeTimeDelay(typeTimeDelay: Long) {
        mTypeTimeDelay = typeTimeDelay
    }

    /**
     * 动画总时长
     */
    private var mAnimTime = 0L

    public fun setAnimTime(animTime: Long) {
        mAnimTime = animTime
    }

    private var mTypeTimer: Timer? = null

    fun startTypeAnim() {
        stopTypeAnim()
        text = ""
        mTypeTimer = Timer()
        if (mAnimTime > 0) {
            val current = text.toString()
            if (current.isNotEmpty()) {
                mTypeTimeDelay = mAnimTime / (current.length)
            }
        }
        mTypeTimer?.schedule(TypeTimerTask(this), 0, mTypeTimeDelay)
        mOnTypeViewListener?.onTypeStart()
    }

    fun stopTypeAnim() {
        mTypeTimer?.cancel()
        mOnTypeViewListener?.onTypeOver()
    }

    private fun updateText() {
        val current = text.toString()
        if (current.length < (this.mShowTextString?.length ?: 0)) {
            text = this.mShowTextString?.substring(
                0,
                current.length + 1
            )
        } else {
            stopTypeAnim()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mTypeTimer?.cancel()
    }

    internal class TypeTimerTask : TimerTask {


        private val view: WeakReference<TypeTextView>

        constructor(typeTextView: TypeTextView) : super() {
            view = WeakReference(typeTextView)
        }


        override fun run() {
            view.get()?.post {
                view.get()?.updateText()
            }
        }
    }
}