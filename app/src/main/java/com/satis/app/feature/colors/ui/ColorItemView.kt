package com.satis.app.feature.colors.ui

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.view.setPadding
import com.satis.app.R
import com.satis.app.feature.colors.persistence.ColorEntity

class ColorItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var clickListener: ((ColorEntity) -> Unit)? = null

    init {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        setPadding(context.resources.getDimension(R.dimen.key_line).toInt())
        gravity = Gravity.CENTER
    }

    fun bind(colorEntity: ColorEntity) {
        with(colorEntity) {
            text = color

            setBackgroundColor(try {
                Color.parseColor(color)
            } catch (t: Throwable) {
                0
            })
            setOnClickListener {
                clickListener?.invoke(this)
            }
        }
    }

}