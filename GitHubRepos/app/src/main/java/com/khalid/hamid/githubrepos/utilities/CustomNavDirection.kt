package com.khalid.hamid.githubrepos.utilities

import android.net.Uri
import androidx.navigation.NavDirections

sealed class CustomNavDirection {
    class NativeNavDirection(val navDirections: NavDirections, val result: Pair<String, Any>? = null) : CustomNavDirection()
    class UriNavDirection(val uri: Uri) : CustomNavDirection()
    object NavigateUpDirection : CustomNavDirection()
    object RootHomeDirection : CustomNavDirection()
}