package com.liyaan.calculationtest.serializable

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.liyaan.calculationtest.R
import kotlinx.android.synthetic.main.activity_serializable.*

class SeriaLizableBudlerActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serializable)
        val bundle = intent.getBundleExtra("studata")
        val stuData = bundle.getParcelable<StudentParcelable>("data")
        Log.i("aaaa","${stuData!!.name}  ${stuData!!.age}  " +
                "${stuData!!.score!!.math}  ${stuData!!.score!!.chinese}  ${stuData!!.score!!.english}")
        stu_name.setText(stuData!!.name)
        stu_age.setText(stuData!!.age.toString())
        stu_math.setText(stuData!!.score!!.math.toString())
        stu_china.setText(stuData!!.score!!.chinese.toString())
        stu_english.setText(stuData!!.score!!.english.toString())
    }
}