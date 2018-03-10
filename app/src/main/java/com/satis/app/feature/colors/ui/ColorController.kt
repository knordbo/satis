package com.satis.app.feature.colors.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.view.postDelayed
import com.satis.app.R
import com.satis.app.conductor.BaseController
import com.satis.app.feature.colors.redux.ColorDispatcher
import com.satis.app.feature.colors.redux.ColorDispatcherViewHolder
import com.satis.app.redux.DispatcherBinder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feature_color.view.*

class ColorController : BaseController(), RecyclerView.OnChildAttachStateChangeListener {

    private val colorDispatcher: ColorDispatcher by lazy { viewModelProvider().get(ColorDispatcherViewHolder::class.java).dispatcher }
    private val disposable = CompositeDisposable()
    private val colorAdapter by lazy { ColorAdapter() }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.feature_color, container, false)

    override fun onViewInflated(view: View) {
        super.onViewInflated(view)
        with(view) {
            (rv.layoutManager as? LinearLayoutManager)?.stackFromEnd = true
            rv.adapter = colorAdapter
            rv.addOnChildAttachStateChangeListener(this@ColorController)
            addColor.setOnClickListener {
                colorDispatcher.addColor(this@ColorController.view!!.color.text.toString())
            }
        }
        DispatcherBinder(lifecycle, colorDispatcher) { colorViewState ->
            with(this.view!!) {
                colorAdapter.bind(colorViewState.colors)
                if (colorViewState.successfullyAddedColor != null) {
                    color.setText("")
                    postDelayed(100) {
                        rv.smoothScrollToPosition(colorAdapter.itemCount - 1)
                    }
                    colorDispatcher.addColorReset()
                }
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

}
