package com.liyaan.calculationtest.paging.pagRoom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
class Student {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    @ColumnInfo(name = "student_number")
    var studentNumber:Int = 0
}