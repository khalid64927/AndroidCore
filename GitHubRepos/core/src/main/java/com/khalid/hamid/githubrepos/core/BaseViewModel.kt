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

package com.khalid.hamid.githubrepos.core

import android.net.Uri
import androidx.annotation.IdRes
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.khalid.hamid.githubrepos.utilities.*
import com.khalid.hamid.githubrepos.utilities.extentions.EspressoIdlingResource
import com.khalid.hamid.githubrepos.utilities.extentions.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), LifecycleObserver, Observable {

    private val _errorEvent = MutableLiveData<ErrorEvent>()
    val errorEvent: LiveData<ErrorEvent>
        get() = _errorEvent

    private val _navigateToLiveEvent = MutableLiveData<Event<CustomNavDirection>>()
    val navigateToLiveEvent: LiveData<Event<CustomNavDirection>>
        get() = _navigateToLiveEvent

    val asyncState: LiveData<AsyncState>
        get() = _asyncState
    protected val _asyncState = MutableLiveData<AsyncState>()

    val clearDataEvent: LiveData<Event<Boolean>>
        get() = _clearDataEvent
    private val _clearDataEvent = MutableLiveData<Event<Boolean>>()

    val analyticsEvent: LiveData<TrackingEvent>
        get() = _analyticsEvent
    protected var _analyticsEvent = MutableLiveData<TrackingEvent>()

    protected fun launch(block: suspend () -> Unit): Job {
        return wrapEspressoIdlingResource {
            viewModelScope.launch(
                CoroutineExceptionHandler { coroutineContext, throwable ->
                    EspressoIdlingResource.decrement()
                    Timber.e(throwable)
                    throwable.message?.run {
                        showError(this)
                    }
                }
            ) {
                EspressoIdlingResource.increment()
                block()
                EspressoIdlingResource.decrement()
            }
        }
    }

    protected fun launchAsyncAPI(error: (Throwable) -> Unit = {}, block: suspend () -> Unit): Job {
        return wrapEspressoIdlingResource {
            viewModelScope.launch(
                CoroutineExceptionHandler { coroutineContext, throwable ->
                    _asyncState.postValue(AsyncState.FAILED)
                    EspressoIdlingResource.decrement()
                    Timber.e(throwable)
                    throwable.message?.run {
                        showError(this)
                    }
                    try {
                        // helper to send error event
                        error(throwable)
                    } catch (e: Exception) {
                        // no-op
                    }
                }
            ) {
                EspressoIdlingResource.increment()
                _asyncState.postValue(AsyncState.LOADING)
                block()
                _asyncState.postValue(AsyncState.LOADED)
                EspressoIdlingResource.decrement()
            }
        }
    }

    internal fun notifyError(e: Throwable) {
        _errorEvent.value = ErrorEvent(e)
    }

    fun showError(errorMsg: String) {
        _errorEvent.postValue(ErrorEvent(Throwable(errorMsg)))
    }

    fun clearDataSuccess(reset: Boolean) {
        _clearDataEvent.postValue(Event(reset))
    }

    fun navigateTo(navigationDirections: NavDirections) {
        _navigateToLiveEvent.postValue(Event(CustomNavDirection.NativeNavDirection(navigationDirections)))
    }

    // Direct id navigation method. Non type-safe, should prefer to use NavDirections instead
    fun navigateTo(@IdRes destId: Int) {
        _navigateToLiveEvent.postValue(
            Event(
                CustomNavDirection.NativeNavDirection(
                    NoArgsNavDirection(destId)
                )
            )
        )
    }
    fun navigateTo(navigationDirections: Uri) {
        _navigateToLiveEvent.postValue(Event(CustomNavDirection.UriNavDirection(navigationDirections)))
    }
    fun navigateUpToHome() {
        _navigateToLiveEvent.postValue(Event(CustomNavDirection.RootHomeDirection))
    }
    fun navigateUp() {
        _navigateToLiveEvent.postValue(Event(CustomNavDirection.NavigateUpDirection))
    }
    fun navigateWithResult(navigationDirections: NavDirections, resultKey: String, result: Any) {
        _navigateToLiveEvent.postValue(Event(CustomNavDirection.NativeNavDirection(navigationDirections, Pair(resultKey, result))))
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks?.notifyCallbacks(this, fieldId, null)
    }

    fun notifyChange() {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks?.notifyCallbacks(this, 0, null)
    }
}

enum class State {
    RUNNING,
    SUCCESS,
    FAILED,
    STOPPED
}

data class AsyncState private constructor(
    val status: State,
    val msg: String? = null,
    val throwable: Throwable? = null,
    val value: Double? = null
) {
    companion object {
        val LOADED = AsyncState(State.SUCCESS)
        val LOADING = AsyncState(State.RUNNING)
        val STOPPED = AsyncState(State.STOPPED)
        val FAILED = AsyncState(State.FAILED)
        fun loadedWithResult(msg: String?) = AsyncState(State.SUCCESS, msg)
        fun loadedWithValue(value: Double) = AsyncState(State.SUCCESS, value = value)
        fun error(msg: String?) = AsyncState(State.FAILED, msg)
        fun error(throwable: Throwable) = AsyncState(State.FAILED, throwable = throwable)
    }
}
sealed class TrackingEvent(val msg: String = "") : Event<String>(msg)
