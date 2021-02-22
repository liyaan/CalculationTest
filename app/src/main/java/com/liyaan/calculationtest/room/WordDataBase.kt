package com.liyaan.calculationtest.room

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = arrayOf(WordEntity::class),version = 1,exportSchema = false)
abstract class WordDataBase:RoomDatabase() {
    abstract fun getWordDao():WordDao
}