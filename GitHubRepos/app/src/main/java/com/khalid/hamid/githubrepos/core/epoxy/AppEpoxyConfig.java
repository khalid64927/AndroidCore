package com.khalid.hamid.githubrepos.core.epoxy;

import com.airbnb.epoxy.EpoxyDataBindingLayouts;
import com.airbnb.epoxy.EpoxyDataBindingPattern;
import com.khalid.hamid.githubrepos.R;

@EpoxyDataBindingPattern(rClass = R.class, layoutPrefix = "view_holder")
@EpoxyDataBindingLayouts({R.layout.view_holder_item_products})
interface AppEpoxyConfig {}
