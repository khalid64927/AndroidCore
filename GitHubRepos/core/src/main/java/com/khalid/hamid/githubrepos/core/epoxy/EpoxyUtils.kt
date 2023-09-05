package com.khalid.hamid.githubrepos.core.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyModel

interface EpoxyDelegate

abstract class CoreEpoxyComponent<in T : EpoxyDelegate> {

    protected val viewId = getAutoGenId()

    abstract fun render(delegate: T): List<EpoxyModel<*>>
}

interface ButtonDelegate : EpoxyDelegate {
    fun onButtonClick()
}

/**
 * Returns an unique auto generated id.
 * This can be used to assign id to the dynamically generated views
 */
fun getAutoGenId(): Int {
    return View.generateViewId()
}