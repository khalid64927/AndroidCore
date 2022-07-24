/*
 * Copyright 2020 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class Repositories @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_column")
    var id: Int = 0,
    @ColumnInfo(name = "author_column") var author: String = "",
    @ColumnInfo(name = "avatar_column") var avatar: String = "",
    @ColumnInfo(name = "description_column") var description: String = "",
    @ColumnInfo(name = "forks_column") var forks: Int = 0,
    @ColumnInfo(name = "language_column") var language: String = "",
    @ColumnInfo(name = "languageColor_column") var languageColor: String = "",
    @ColumnInfo(name = "name_column") var name: String = "",
    @ColumnInfo(name = "stars_column") var stars: Int = 0,
    @ColumnInfo(name = "url_column") var url: String = ""
) {
    @Ignore
    var builtBy: List<BuiltBy> = listOf()
}
