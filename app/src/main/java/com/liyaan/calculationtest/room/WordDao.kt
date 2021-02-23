package com.liyaan.calculationtest.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert
    fun insertWords(vararg entity: WordEntity)

    @Update
    fun updateWords(vararg entity: WordEntity)

    @Delete
    fun deleteWords(vararg entity: WordEntity)

    @Query("DELETE FROM WORDENTITY")
    fun deleteAllWords()

    @Query("SELECT * FROM WORDENTITY ORDER BY ID DESC")
//    fun getAllWords():MutableList<WordEntity>
    fun getAllWords():LiveData<MutableList<WordEntity>>
}