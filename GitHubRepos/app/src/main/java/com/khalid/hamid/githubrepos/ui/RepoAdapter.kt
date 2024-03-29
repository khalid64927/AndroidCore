/*
 * Copyright 2020 Mohammed Khalid Hamid.
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
            return oldItem.avatar == newItem.avatar &&
                oldItem.description == newItem.description
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
