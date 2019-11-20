package com.khalid.hamid.githubrepos.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "word_table")
data class Word(
    @Expose
    @SerializedName("word")
    @PrimaryKey
    @ColumnInfo(name = "word_column")
    val word: String)