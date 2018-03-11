package com.satis.app.feature.colors.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.view.postDelayed
import com.satis.app.R
import com.satis.app.conductor.BaseController
import com.satis.app.feature.colors.redux.ColorDispatcherViewHolder
import com.satis.app.feature.colors.redux.ColorViewState
import com.satis.app.redux.DispatcherBinder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feature_color.view.*

class ColorController : BaseController(), RecyclerView.OnChildAttachStateChangeListener {

    private val colorDispatcher by lazy { viewModelProvider().get(ColorDispatcherViewHolder::class.java).dispatcher }
    private val colorDispatcherBinder by lazy { DispatcherBinder(lifecycle, colorDispatcher, this::render) }
    private val disposable = CompositeDisposable()
    private val colorAdapter = ColorAdapter()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.feature_color, container, false)

    override fun onViewInflated(view: View) {
        super.onViewInflated(view)
        colorDispatcherBinder // invoke to ensure initialised
        with(view) {
            (rv.layoutManager as? LinearLayoutManager)?.stackFromEnd = true
            rv.adapter = colorAdapter
            rv.addOnChildAttachStateChangeListener(this@ColorController)
            addColor.setOnClickListener {
                colorDispatcher.addColor(this@ColorController.view!!.color.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onChildViewDetachedFromWindow(view: View) {
        if (view is ColorItemView) {
            view.clickListener = null
        }
    }

    override fun onChildViewAttachedToWindow(view: View) {
        if (view is ColorItemView) {
            view.clickListener = colorDispatcher::deleteColor
        }
    }

    private fun render(colorViewState: ColorViewState) {
        with(view!!) {
            colorAdapter.bind(colorViewState.colors)
            if (colorViewState.successfullyAddedColor != null) {
                color.setText("")
                postDelayed(100) {
                    rv.smoothScrollToPosition(colorAdapter.itemCount - 1)
                }
                colorDispatcher.addColorReset()
            }
            if (colorViewState.successfullyDeletedColor != null) {
                colorDispatcher.deleteColorReset()
            }
        }
    }

}
