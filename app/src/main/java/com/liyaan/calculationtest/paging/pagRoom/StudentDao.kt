package com.liyaan.calculationtest.paging.pagRoom

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StudentDao {
    @Insert
    fun insertStudent(vararg student:Student)
    @Query("DELETE FROM student_table")
    fun deleteAllStudents()
    @Query("SELECT * FROM student_table ORDER BY ID")
    fun getAllStudents(): DataSource.Factory<Int,Student>
}