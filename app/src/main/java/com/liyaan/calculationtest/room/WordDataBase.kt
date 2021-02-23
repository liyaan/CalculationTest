package com.liyaan.calculationtest.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(WordEntity::class),version = 4,exportSchema = false)
abstract class WordDataBase:RoomDatabase() {



    companion object{

        val migration:Migration =object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE wordentity ADD COLUMN foo_data INTEGER NOT NULL DEFAULT 1")
            }

        }
        val migration2_3:Migration =object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL , username TEXT NOT NULL, userpassword TEXT NOT NULL)")
                database.execSQL("INSERT INTO word_temp (id , username , userpassword)  SELECT id , username , userpassword FROM wordentity")
                database.execSQL("DROP TABLE wordentity")
                database.execSQL("ALTER TABLE word_temp RENAME TO wordentity")
            }

        }
        val migration3_4:Migration =object :Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE wordentity ADD COLUMN password_invisibe INTEGER NOT NULL DEFAULT 1")
            }

        }
        var INSTANCE:WordDataBase? = null
        @JvmStatic
        @Synchronized
        fun getDataBase(context: Context):WordDataBase{
            if (INSTANCE==null){
                //fallbackToDestructiveMigration 数据库（添加字段）升级 将所有的数据全部清除，重新创建数据
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WordDataBase::class.java,"word_database")
                        .addMigrations(migration3_4)
                        .build()
            }
            return INSTANCE!!
        }
    }
    abstract fun getWordDao():WordDao


}