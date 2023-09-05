/*
 * Copyright 2023 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.core.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyModel

interface EpoxyDelegate

abstract class CoreEpoxyComponent<in T : EpoxyDelegate> {

    protected val viewId = getAutoGenId()

    abstract fun render(delegate: T): List<EpoxyModel<*>>
}

interface ButtonDelegate : EpoxyDelegate {
    fun onButtonClick()
}

/**
 * Returns an unique auto generated id.
 * This can be used to assign id to the dynamically generated views
 */
fun getAutoGenId(): Int {
    return View.generateViewId()
}
