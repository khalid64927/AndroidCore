package com.khalid.hamid.githubrepos.ui.timeline.dto

import com.google.gson.annotations.SerializedName



class ProductList(): ArrayList<ProductsItem>()
data class ProductsItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("num_likes")
    val numLikes: Int,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("status")
    val status: String
)