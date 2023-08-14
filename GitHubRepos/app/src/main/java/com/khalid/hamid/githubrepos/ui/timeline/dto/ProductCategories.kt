package com.khalid.hamid.githubrepos.ui.timeline.dto

import com.google.gson.annotations.SerializedName


class ProductCategoriesList(): ArrayList<ProductCategoriesItem>()
data class ProductCategoriesItem(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("name")
    val name: String
)