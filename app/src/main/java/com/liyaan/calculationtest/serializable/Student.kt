package com.liyaan.calculationtest.serializable

import java.io.Serializable

data class Student(var name:String,var age:Int,var score:Score):Serializable

class Score:Serializable{
    var grade:String
    var math:Int = 0
    var english:Int = 0
    var chinese:Int = 0
    constructor(math:Int,english:Int,chinese:Int){
        this.math = math
        this.english = english
        this.chinese = chinese
        if (math>=90 && english>=90 && chinese>=90){
            this.grade = "A"
        }else if (math>=80 && english>=80 && chinese>=80){
            this.grade = "B"
        }else{
            this.grade = "C"
        }
    }
}