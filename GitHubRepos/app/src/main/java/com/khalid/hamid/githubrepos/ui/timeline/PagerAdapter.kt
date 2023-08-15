package com.khalid.hamid.githubrepos.ui.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.Locale.Category

class PagerAdapter(val fragment: FragmentActivity,
                   val receivedProducts: ReceivedProducts): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return receivedProducts.categoriesList.size
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        val categoryName = receivedProducts.categoriesList[position].name
        val categoryUrl = receivedProducts.categoriesList[position].data
        bundle.putString("categoryName", categoryName)
        bundle.putString("categoryUrl", categoryUrl)
        val fragment = ProductsTimelineFragment()
        fragment.arguments = bundle
        return fragment
    }

}