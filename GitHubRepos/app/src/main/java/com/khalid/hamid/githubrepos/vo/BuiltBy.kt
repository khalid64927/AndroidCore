package com.khalid.hamid.githubrepos.vo


import androidx.room.Entity

@Entity
data class BuiltBy(
    val avatar: String = "",
    val href: String = "",
    val username: String = ""
){
    constructor():this ("","","")
}
