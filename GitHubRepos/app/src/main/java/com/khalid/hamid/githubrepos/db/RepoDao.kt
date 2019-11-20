package com.khalid.hamid.githubrepos.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khalid.hamid.githubrepos.vo.Repositories


/**
 * Interface for database access on Repositories related operations.
 */
@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRepos(repositories: List<Repositories>)

    @Query("SELECT * FROM repo_table")
    abstract suspend fun getRepoList(): List<Repositories>

}