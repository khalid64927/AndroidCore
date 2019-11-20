package com.khalid.hamid.githubrepos.db

import androidx.room.Dao
import androidx.room.Query
import com.khalid.hamid.githubrepos.vo.Word


@Dao
abstract class WordDao {

    @Query("SELECT * from word_table")
    abstract fun getWords(): List<Word>


}