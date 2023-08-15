package com.khalid.hamid.githubrepos.core.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.khalid.hamid.githubrepos.ItemProductsBindingModel_
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductsItem

interface MercariDelegate

abstract class MercariEpoxyComponent<in T : MercariDelegate> {

    protected val viewId = getAutoGenId()

    abstract fun render(delegate: T): List<EpoxyModel<*>>
}

interface ButtonDelegate : MercariDelegate {
    fun onButtonClick()
}


/**
 * Returns an unique auto generated id.
 * This can be used to assign id to the dynamically generated views
 */
fun getAutoGenId(): Int {
    return View.generateViewId()
}


data class MercariProductComponent(val item: ProductsItem) : MercariEpoxyComponent<MercariDelegate>() {
    override fun render(delegate: MercariDelegate): List<EpoxyModel<*>> {
        return listOf(
            ItemProductsBindingModel_().id(item.id).item(item)
        )
    }

}
