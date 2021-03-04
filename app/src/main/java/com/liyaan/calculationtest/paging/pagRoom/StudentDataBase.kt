package com.liyaan.calculationtest.paging.pagRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Student::class),version = 1,exportSchema = false)
abstract class StudentDataBase: RoomDatabase()  {

    companion object{
        var INSTANCE: StudentDataBase? = null
        @JvmStatic
        @Synchronized
        fun getDataBase(context: Context):StudentDataBase{
            if (INSTANCE ==null){
                //fallbackToDestructiveMigration 数据库（添加字段）升级 将所有的数据全部清除，重新创建数据
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StudentDataBase::class.java,"student_database")
                        .build()
            }
            return INSTANCE!!
        }
    }
    abstract fun getStudentDao(): StudentDao
}