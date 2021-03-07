package com.liyaan.calculationtest.serializable

import android.os.Parcel
import android.os.Parcelable

data class StudentParcelable(var name: String?, var age:Int, var score:ScoreParcelable?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readParcelable(ScoreParcelable::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeParcelable(score, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentParcelable> {
        override fun createFromParcel(parcel: Parcel): StudentParcelable {
            return StudentParcelable(parcel)
        }

        override fun newArray(size: Int): Array<StudentParcelable?> {
            return arrayOfNulls(size)
        }
    }
}

class ScoreParcelable(math:Int,english:Int,chinese:Int) :Parcelable{

    var math:Int = 0
    var english:Int = 0
    var chinese:Int = 0

    constructor(parcel: Parcel) : this(parcel.readInt(),parcel.readInt(),parcel.readInt()) {
    }


    init{
        this.math = math
        this.english = english
        this.chinese = chinese
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(grade)
        parcel.writeInt(math)
        parcel.writeInt(english)
        parcel.writeInt(chinese)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScoreParcelable> {
        override fun createFromParcel(parcel: Parcel): ScoreParcelable {
            return ScoreParcelable(parcel)
        }

        override fun newArray(size: Int): Array<ScoreParcelable?> {
            return arrayOfNulls(size)
        }
    }

}