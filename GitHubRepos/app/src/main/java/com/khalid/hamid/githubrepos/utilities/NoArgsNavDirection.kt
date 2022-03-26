package com.khalid.hamid.githubrepos.utilities

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections

/**
 * Nav direction implementation when there is no action generated class
 * or arguments to be passed. Used by base fragment to navigate
 */
class NoArgsNavDirection(@IdRes val destId: Int,
                         override val actionId: Int = -1,
                         override val arguments: Bundle = Bundle()
) : NavDirections
