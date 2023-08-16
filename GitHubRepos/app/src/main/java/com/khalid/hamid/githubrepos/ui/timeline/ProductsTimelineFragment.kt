/*
 * Copyright 2023 Mohammed Khalid Hamid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.khalid.hamid.githubrepos.ui.timeline

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.core.epoxy.MercariDelegate
import com.khalid.hamid.githubrepos.core.epoxy.MercariProductComponent
import com.khalid.hamid.githubrepos.databinding.FragmentTimelineProductsBinding
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.utilities.GridSpacingItemDecoration
import com.khalid.hamid.githubrepos.utilities.ItemDivider
import com.khalid.hamid.githubrepos.utilities.delegates.fragmentViewLifecycleDelegate

class ProductsTimelineFragment : BaseFragment(), MercariDelegate {

    override val mLayout = R.layout.fragment_timeline_products

    private val v by fragmentViewLifecycleDelegate<FragmentTimelineProductsBinding>({
        getFragmentBinding()
    })

    private val vm: ProductsTimelineViewModel by fragmentViewLifecycleDelegate({
        getViewModel()
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val categoryName = getString("categoryName") ?: "All"
            val categoryUrl = getString("categoryUrl") ?: ""
            v.ervProducts.layoutManager = GridLayoutManager(context, 2)
            context?.run {
                val spanCount = 2 // 3 columns
                val spacing = 50 // 50px
                val includeEdge = false
                val itemDivider =
                    ItemDivider(ContextCompat.getDrawable(this, R.drawable.horizontal_line))
                val gridDecoration = GridSpacingItemDecoration(spanCount, spacing, includeEdge)
                v.ervProducts.addItemDecoration(gridDecoration)
            }

            vm.getTimelineProducts(categoryName)

            vm.productTimelineEventLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is ReceivedProductsEvent -> {
                        updateEpoxyRecyclerView(it.productList)
                    }

                    is FailedToFetchTimelineProducts -> {
                    }
                }
            }
        }
    }

    private fun updateEpoxyRecyclerView(productList: ProductList) {
        v.ervProducts.withModels {
            productList.forEach {
                MercariProductComponent(it)
                    .render(this@ProductsTimelineFragment)
                    .forEach { model ->
                        model.addTo(this)
                    }
            }
        }
    }
}
interface ProductsTimelineFragmentUiActions {
    // Add actions for this screen here
    fun sellProductClicked()
}
