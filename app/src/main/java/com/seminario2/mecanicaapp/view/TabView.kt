package com.seminario2.mecanicaapp.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.gone
import com.seminario2.mecanicaapp.commons.extension.visible
import kotlinx.android.synthetic.main.view_tab.view.*


class TabView : ConstraintLayout {

    var homeListener: OnClickListener? = null
    var searchListener: OnClickListener? = null

    private var active: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : this(
        context,
        attrs,
        defStyle,
        0
    )

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) : super(
        context,
        attrs
    ) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_tab, this)
        getAttrsd(attrs)
        setActive()
        addListener()
    }

    private fun addListener() {
        fr_tab_inicio.setOnClickListener {
            homeListener?.onClick(it)
        }
        fr_tab_buscar.setOnClickListener {
            searchListener?.onClick(it)
        }
    }

    private fun getAttrsd(attrs: AttributeSet?) {
        attrs?.let {
            val stylesAttributes: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.TabView, 0, 0)
            active = stylesAttributes.getInteger(R.styleable.TabView_tabActive, 0)
        }
    }

    fun setActive() {
        when (active) {
            1 -> {
                fr_tab_inicio_line.gone()
                fr_tab_buscar_line.visible()
            }
            else -> {
                fr_tab_inicio_line.visible()
                fr_tab_buscar_line.gone()
            }
        }

    }
}