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

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.khalid.hamid.githubrepos.R

/**
 * This file is meant different configurations of toolbar by extending [ToolbarConfig],
 * creating extension functions or placing commonly used menu items
 */

// Default values for toolbar config
open class BaseToolbar(
    showToolbar: Boolean = true,
    showHomeIcon: Boolean = false,
    @DrawableRes iconDrawableRes: Int? = null,
    @ColorRes bgColorRes: Int = R.color.colorActionBar,
    @ColorRes titleTextColorRes: Int = R.color.real_black,
    @ColorRes backArrowColorRes: Int = R.color.real_black,
    showOverflow: Boolean = true,
    @ColorRes overflowColorRes: Int = R.color.colorActionBarIcons,
    menuConfig: List<ToolbarMenuConfig> = mutableListOf()
) : ToolbarConfig(
    showToolbar = showToolbar,
    showHomeIcon = showHomeIcon,
    iconDrawable = iconDrawableRes,
    bgColor = bgColorRes,
    titleTextColor = titleTextColorRes,
    backArrowColor = backArrowColorRes,
    showOverFlow = showOverflow,
    overflowColor = overflowColorRes,
    toolbarMenuConfig = menuConfig
)

class DefaultToolbar : BaseToolbar()
class ToolbarWithUp : BaseToolbar(showHomeIcon = true)
class ToolbarOnlyLogo : BaseToolbar(
    // TODO: change ic_launcher_background
    showHomeIcon = false, iconDrawableRes = R.drawable.ic_launcher_background, showOverflow = false
)

// No need overflow menu when nav drawer is available
class ToolbarWithNav : BaseToolbar(showHomeIcon = true, showOverflow = false)
object NoToolbar : BaseToolbar(showToolbar = false)

/**
 * Base toolbar overflow menu options that all toolbars with overflow menu should include
 */
fun getBaseToolbarMenuOption(clearCacheAction: () -> Unit) = ToolbarMenuConfig(
    R.menu.default_toolbar_menu,
    listOf(
        ToolbarMenuAction(R.id.clear_cache) {
            clearCacheAction.invoke()
        }
    )
)

/**
 * Extension function of ToolbarConfig can be placed here to configure toolbar
 */
// Logo can be tagged to any toolbar and don't need its own class
fun ToolbarConfig.withLogo(): ToolbarConfig {
    // TODO: change ic_launcher_background
    iconDrawable = R.drawable.ic_launcher_background
    return this
}
