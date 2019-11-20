package com.khalid.hamid.githubrepos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khalid.hamid.githubrepos.vo.Repositories


/**
 * Main database description.
 */
@Database(
    entities = [
    Repositories::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDb:RoomDatabase() {

    abstract fun getRepoDao(): RepoDao

}