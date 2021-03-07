package com.liyaan.calculationtest.serializable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.liyaan.calculationtest.R
import kotlinx.android.synthetic.main.activity_serializable.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class SeriaLizableActivity:AppCompatActivity() {
    val fileName:String = "hello_stu"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serializable)
        stu_save.setOnClickListener {
            var stuName = stu_name.text.toString()
            var stuAge = stu_age.text.toString().toInt()
            var stuMath = stu_math.text.toString().toInt()
            var stuChina = stu_china.text.toString().toInt()
            var stuEnglish = stu_english.text.toString().toInt()
            val scoreParcelable = ScoreParcelable(stuMath,stuEnglish,stuChina)
            val stuPerBean = StudentParcelable(stuName,stuAge,scoreParcelable)
            Log.i("aaaa","$stuName  $stuAge  $stuMath  $stuChina  $stuEnglish")
            Log.i("aaaa","${stuPerBean.name}  ${stuPerBean.age}  " +
                    "${stuPerBean.score!!.math}  ${stuPerBean.score!!.chinese} ${stuPerBean.score!!.english} ")
            val intent = Intent(this@SeriaLizableActivity,SeriaLizableBudlerActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("data",stuPerBean)
            intent.putExtra("studata",bundle)
            startActivity(intent)
//            val save = Score(stuMath,stuEnglish,stuChina)
//            val stuBean = Student(stuName,stuAge,save)
//            try {
//                val objectOutputStream = ObjectOutputStream(openFileOutput(fileName, Context.MODE_PRIVATE))
//                objectOutputStream.writeObject(stuBean)
//                objectOutputStream.flush()
//                objectOutputStream.close()
//                objectOutputStream == null
//                stu_name.text.clear()
//                stu_age.text.clear()
//                stu_math.text.clear()
//                stu_china.text.clear()
//                stu_english.text.clear()
//            }catch (e:Exception){
////                Log.i("aaaa",e.message)
//                e.printStackTrace()
//            }

        }
        stu_load.setOnClickListener {
            val objectInputStream = ObjectInputStream(openFileInput(fileName))
            val student:Student = objectInputStream.readObject() as Student

            stu_name.setText(student.name)
            stu_age.setText(student.age.toString())
            stu_math.setText(student.score!!.math.toString())
            stu_china.setText(student.score!!.chinese.toString())
            stu_english.setText(student.score!!.english.toString())
            stu_grade.setText(student.score!!.grade)
            objectInputStream.close()
        }
    }
}