package com.khalid.hamid.githubrepos.utilities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.MenuRes

abstract class ToolbarConfig(
    var showToolbar: Boolean,
    var showHomeIcon: Boolean,
    @DrawableRes var iconDrawable: Int?,
    @ColorRes var bgColor: Int,
    @ColorRes var titleTextColor: Int,
    @ColorRes var backArrowColor: Int,
    var showOverFlow: Boolean,
    @ColorRes var overflowColor: Int,
    var toolbarMenuConfig: List<ToolbarMenuConfig>
)

data class ToolbarMenuConfig(@MenuRes val menuRes: Int, val menuActionList: List<ToolbarMenuAction>)
data class ToolbarMenuAction(@IdRes val menuItemRes: Int, val menuAction: () -> Unit)