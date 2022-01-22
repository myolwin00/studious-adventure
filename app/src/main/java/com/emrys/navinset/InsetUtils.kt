package com.emrys.navinset

import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.emrys.navinset.InsetUtils.doOnApplyWindowInsets

object InsetUtils {

    fun View.applySystemWindowInsetsPadding(
        applyLeft: Boolean = false,
        applyTop: Boolean = false,
        applyRight: Boolean = false,
        applyBottom: Boolean = false
    ) {
        val initialPadding = recordInitialPaddingForView(this)

        doOnApplyWindowInsets { view, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val left = if (applyLeft) systemBarInsets.left else 0
            val top = if (applyTop) systemBarInsets.top else 0
            val right = if (applyRight) systemBarInsets.right else 0
            val bottom = if (applyBottom) systemBarInsets.bottom else 0

            view.setPadding(
                initialPadding.left + left,
                initialPadding.top + top,
                initialPadding.right + right,
                initialPadding.bottom + bottom
            )
        }
    }

    fun View.applySystemWindowInsetsMargin(
        applyLeft: Boolean = false,
        applyTop: Boolean = false,
        applyRight: Boolean = false,
        applyBottom: Boolean = false
    ) {

        val initialMargin = recordInitialMarginForView(this)

        doOnApplyWindowInsets { view, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val left = if (applyLeft) systemBarInsets.left else 0
            val top = if (applyTop) systemBarInsets.top else 0
            val right = if (applyRight) systemBarInsets.right else 0
            val bottom = if (applyBottom) systemBarInsets.bottom else 0

            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = initialMargin.left + left
                topMargin = initialMargin.top + top
                rightMargin = initialMargin.right + right
                bottomMargin = initialMargin.bottom + bottom
            }
        }
    }

    fun View.doOnApplyWindowInsets(
        block: (View, WindowInsetsCompat) -> Unit
    ) {
        // Set an actual OnApplyWindowInsetsListener which proxies to the given
        // lambda, also passing in the original padding & margin states
        setOnApplyWindowInsetsListener { v, insets ->
            block(v, insets.compat())
            // Always return the insets, so that children can also use them
            insets
        }

        // request some insets
        requestApplyInsetsWhenAttached()
    }

    private fun View.requestApplyInsetsWhenAttached() {
        if (isAttachedToWindow) {
            requestApplyInsets()
        } else {
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    v.removeOnAttachStateChangeListener(this)
                    v.requestApplyInsets()
                }

                override fun onViewDetachedFromWindow(v: View) = Unit
            })
        }
    }

    private fun recordInitialPaddingForView(view: View) = InitialSpacing(
        view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
    )

    private fun recordInitialMarginForView(view: View): InitialSpacing {
        return InitialSpacing(view.marginLeft, view.marginTop, view.marginRight, view.marginBottom)
    }

    private class InitialSpacing(val left: Int, val top: Int, val right: Int, val bottom: Int)
}