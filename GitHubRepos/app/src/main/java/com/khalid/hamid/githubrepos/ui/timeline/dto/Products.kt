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

package com.khalid.hamid.githubrepos.ui.timeline.dto

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

class ProductList() : ArrayList<ProductsItem>()

@Entity(
    tableName = "products",
    primaryKeys = ["id"],
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
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
    val status: String,
    var categoryId: String = ""
)
