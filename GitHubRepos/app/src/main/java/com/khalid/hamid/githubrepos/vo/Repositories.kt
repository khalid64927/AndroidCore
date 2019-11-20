package com.khalid.hamid.githubrepos.vo


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class Repositories @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_column") var id: Int = 0,
    @ColumnInfo (name = "author_column") var author: String = "",
    @ColumnInfo (name = "avatar_column") var avatar: String = "",
    @Ignore
    var builtBy: List<BuiltBy> = listOf(),
    @ColumnInfo (name = "description_column") var description: String = "",
    @ColumnInfo (name = "forks_column") var forks: Int = 0,
    @ColumnInfo (name = "language_column") var language: String = "",
    @ColumnInfo (name = "languageColor_column") var languageColor: String = "",
    @ColumnInfo (name = "name_column") var name: String = "",
    @ColumnInfo (name = "stars_column") var stars: Int = 0,
    @ColumnInfo (name = "url_column") var url: String = ""
)
