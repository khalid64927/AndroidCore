package com.khalid.hamid.githubrepos.ui

import android.app.Application
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.utilities.AppExecutors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepoFragment2: BaseFragment() {

    override val mLayout = R.layout.fragment_repo
    override fun getPageName() = ""


    @Inject
    lateinit var executors: AppExecutors

    @Inject
    lateinit var app: Application
}