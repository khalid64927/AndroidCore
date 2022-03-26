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

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections

/**
 * Nav direction implementation when there is no action generated class
 * or arguments to be passed. Used by base fragment to navigate
 */
class NoArgsNavDirection(
    @IdRes val destId: Int,
    override val actionId: Int = -1,
    override val arguments: Bundle = Bundle()
) : NavDirections
