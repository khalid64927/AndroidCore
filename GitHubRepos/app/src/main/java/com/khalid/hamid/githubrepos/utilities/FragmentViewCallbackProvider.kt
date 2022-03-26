package com.khalid.hamid.githubrepos.utilities

interface FragmentViewCallbackProvider {
    var onViewCreatedListeners: MutableList<OnViewCreatedListener>
    fun addOnViewCreatedListener(onViewCreatedListener: OnViewCreatedListener)
    fun removeOnViewCreatedListener(onViewCreatedListener: OnViewCreatedListener)
}