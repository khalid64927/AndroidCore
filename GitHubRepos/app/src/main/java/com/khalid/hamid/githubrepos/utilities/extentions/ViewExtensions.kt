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

package com.khalid.hamid.githubrepos.utilities.extentions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoView
import com.khalid.hamid.githubrepos.R
import java.lang.StringBuilder

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

@BindingAdapter("app:productImage")
fun PhotoView.loadSKUImage(imageURL: String?) {
    Glide.with(context)
        .load(imageURL)
        .apply(
            RequestOptions().placeholder(R.drawable.horizontal_line)
        )
        .into(this)
}

@BindingAdapter("app:productStatus")
fun ImageView.checkStatus(value: String) {
    visibility = if (value == "sold_out") View.VISIBLE else View.GONE
}

@BindingAdapter("app:integerData")
fun TextView.setIntegerData(value: Int) {
    text = value.toString()
}

@BindingAdapter("app:productPrice")
fun TextView.setPrice(value: Int) {
    val sb = StringBuilder()
    sb.append("$ $value")
    text = sb.toString()
}
