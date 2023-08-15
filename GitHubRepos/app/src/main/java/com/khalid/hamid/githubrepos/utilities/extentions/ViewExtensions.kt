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
fun TextView.setIntegerData(value: Int){
    text = value.toString()
}

@BindingAdapter("app:productPrice")
fun TextView.setPrice(value: Int){
    val sb = StringBuilder()
    sb.append("$ $value")
    text = sb.toString()
}
