package com.khalid.hamid.githubrepos.utilities

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import timber.log.Timber
import java.lang.Exception

/**
 * No checks for action as direct fragment id can be navigated using this function
 *
 * Should not navigate action ID directly. Prefer safeargs (NavDirections) param
 * @see NavController.safeNavigate(directions: NavDirections)
 */
fun NavController.safeNavigate(resId: Int, args: Bundle? = null): Boolean {
    return try {
        navigate(resId, args)
        true
    } catch (e: Exception) {
        Timber.e(e)
        false
    }
}

/**
 * Get current fragment's action or graph's action (as it may be a global action)
 * Check the action's intended destination is valid before navigating
 *
 * This may be invalid due to duplicate navigate calls using the same action Id;
 * causing the 2nd call to be called in the new fragment and failing to find the action's destination
 */
fun NavController.safeNavigate(directions: NavDirections): Boolean {
    try {
        val action = currentDestination?.getAction(directions.actionId) ?: graph.getAction(directions.actionId)
        if (action != null && currentDestination?.id != action.destinationId) {
            navigate(directions)
            return true
        }
        return false
    } catch (e: Exception) {
        Timber.e(e)
        return false
    }
}

fun NavController.safeNavigate(
    deepLink: Uri,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    try {
        navigate(deepLink, navOptions, navigatorExtras)
    } catch (e: Exception) {
        Timber.e(e)
    }
}
