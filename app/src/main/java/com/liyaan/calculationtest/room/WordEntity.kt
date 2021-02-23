package com.liyaan.calculationtest.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WordEntity {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    @ColumnInfo(name = "username")
    var name:String
    @ColumnInfo(name = "userpassword")
    var password:String
    @ColumnInfo(name = "password_invisibe")
    var passwordInvisibe:Boolean = false

    constructor(name: String, password: String) {
        this.name = name
        this.password = password
    }
}