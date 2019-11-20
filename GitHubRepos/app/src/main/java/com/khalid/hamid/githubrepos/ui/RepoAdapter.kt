package com.khalid.hamid.githubrepos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.databinding.ItemMainBinding
import com.khalid.hamid.githubrepos.utilities.AppExecutors
import com.khalid.hamid.githubrepos.vo.Repositories

class RepoAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((Repositories) -> Unit)?
) : DataBoundListAdapter<Repositories, ItemMainBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Repositories>() {
        override fun areItemsTheSame(oldItem: Repositories, newItem: Repositories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repositories, newItem: Repositories): Boolean {
            return oldItem.avatar == newItem.avatar
                    && oldItem.description == newItem.description
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemMainBinding {
        val binding = DataBindingUtil
            .inflate<ItemMainBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_main,
                parent,
                false,
                dataBindingComponent
            )
        binding.root.setOnClickListener {
            binding.repos?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemMainBinding, item: Repositories) {
        binding.repos = item
    }

}

