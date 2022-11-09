package com.shixingxing

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.lang.ref.WeakReference
import java.util.*

/**
 * 类描述:打字机效果自定义类
 * 出处:https://github.com/zmywly8866/TypeTextView
 */
open class TypeTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        private const val TYPE_TIME_DELAY = 100

    }

    interface OnTypeViewListener {
        fun onTypeOver()
        fun onTypeStart()
    }

    var mOnTypeViewListener: OnTypeViewListener? = null
    var mShowTextString: String? = null
    private val mTypeTimeDelay = TYPE_TIME_DELAY
    private var mTypeTimer: Timer? = null


    fun startTypeTimer() {
        stopTypeTimer()
        text = ""
        mTypeTimer = Timer()
        mTypeTimer?.schedule(TypeTimerTask(this), mTypeTimeDelay.toLong(), mTypeTimeDelay.toLong())
    }

    fun stopTypeTimer() {
        mTypeTimer?.cancel()
    }

    private fun updateText() {
        if (this.text.toString().length < (this.mShowTextString?.length ?: 0)) {
            this.text = this.mShowTextString?.substring(
                0,
                this.text.toString().length + 1
            )
        } else {
            this.stopTypeTimer()
            this.mOnTypeViewListener?.onTypeOver()
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