/*
 * Copyright 2022 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.utilities

import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FragmentViewLifecycleDelegate<T : Any>(
    val fragment: Fragment,
    private val fragmentViewCallbackProvider: FragmentViewCallbackProvider,
    val initializer: () -> T,
    val doBeforeClear: ((T?) -> Unit)? = null
) : ReadWriteProperty<Fragment, T>, OnViewCreatedListener {
    private var _value: T? = null
        get() {
            val state = fragment.lifecycle.currentState
            if (!state.isAtLeast(Lifecycle.State.CREATED)) {
                throw java.lang.IllegalStateException("This value can only be accessed after onCreate. State now is $state ")
            }
            return field
        }

    init {
        fragmentViewCallbackProvider.addOnViewCreatedListener(this)

        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(
                    fragment
                ) { viewLifecycleOwner ->

                    // Observe the fragment's viewlifecycle to clear object onViewDestroyed
                    viewLifecycleOwner?.lifecycle?.addObserver(object :
                            DefaultLifecycleObserver {
                            override fun onDestroy(owner: LifecycleOwner) {
                                doBeforeClear?.invoke(_value)
                                _value = null
                            }
                        })
                }
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragmentViewCallbackProvider.removeOnViewCreatedListener(this@FragmentViewLifecycleDelegate)
            }
        })
    }

    override fun onViewCreateCalled() {
        if (_value == null) {
            _value = initializer.invoke()
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
            "should never call fragmentViewLifecycleDelegate get when it might not be available"
        )
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

interface OnViewCreatedListener {
    fun onViewCreateCalled()
}

/**
 * Creates an [FragmentViewLifecycleDelegate] associated with this fragment.
 */
fun <T : Any> Fragment.fragmentViewLifecycleDelegate(
    fragmentViewCallbackProvider: FragmentViewCallbackProvider,
    initializer: () -> T,
    doBeforeClear: ((T?) -> Unit)? = null
) = FragmentViewLifecycleDelegate(this, fragmentViewCallbackProvider, initializer, doBeforeClear)

/**
 * Creates an [FragmentViewLifecycleDelegate] with fragment as FragmentViewCallbackProvider.
 */
fun <T : Any> Fragment.fragmentViewLifecycleDelegate(
    initializer: () -> T,
    doBeforeClear: ((T?) -> Unit)? = null
) = FragmentViewLifecycleDelegate(
    this,
    this as? FragmentViewCallbackProvider
        ?: throw IllegalArgumentException("${this.javaClass} does not implement FragmentViewCallbackProvider"),
    initializer,
    doBeforeClear
)

/**
 * Creates an [FragmentViewLifecycleDelegate] with custom dismiss dialog action on view destroy.
 */
fun <T : Dialog> Fragment.autoClearDialog(
    initializer: () -> T,
    doBeforeClear: ((T?) -> Unit)? = null
) = FragmentViewLifecycleDelegate(
    this,
    this as? FragmentViewCallbackProvider
        ?: throw IllegalArgumentException("${this.javaClass} does not implement FragmentViewCallbackProvider"),
    initializer,
    {
        it?.dismiss()
    }
)
