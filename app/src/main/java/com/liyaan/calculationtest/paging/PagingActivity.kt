package com.liyaan.calculationtest.paging

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.liyaan.calculationtest.R
import com.liyaan.calculationtest.paging.adapter.PagingAdapter
import com.liyaan.calculationtest.paging.pagRoom.Student
import com.liyaan.calculationtest.paging.pagRoom.StudentDao
import com.liyaan.calculationtest.paging.pagRoom.StudentDataBase
import kotlinx.android.synthetic.main.activity_paging.*


class PagingActivity: AppCompatActivity() {
    var studentDao:StudentDao? = null
    var studentDataBase:StudentDataBase? = null
    var pagingAdapter:PagingAdapter? = null
    var allStudentsLivePaged: LiveData<PagedList<Student>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        initView()
        pagingAdapter = PagingAdapter()
        recyclerView.adapter = pagingAdapter
        studentDataBase = StudentDataBase.getDataBase(this)
        studentDao = studentDataBase!!.getStudentDao()
        allStudentsLivePaged = LivePagedListBuilder(studentDao!!.getAllStudents(),2).build()
        allStudentsLivePaged!!.observe(this,object:Observer<PagedList<Student>>{
            override fun onChanged(students: PagedList<Student>?) {
                pagingAdapter!!.submitList(students!!)
                students!!.addWeakCallback(null, object : PagedList.Callback() {
                    override fun onChanged(position: Int, count: Int) {
                        Log.d("myLog", "onChanged: $students")
                    }

                    override fun onInserted(position: Int, count: Int) {}
                    override fun onRemoved(position: Int, count: Int) {}
                })
            }
        })
        buttonPopulate.setOnClickListener {
//            val stus = arrayOfNulls<Student>(1002)
//            for(i in 0..1000){
//                var student = Student()
////                student.id = i
//                student.studentNumber = i
//                stus[i] = student
//            }
            val students = arrayOfNulls<Student>(1000)
            for (i in 0..999) {
                val student = Student()
                student.studentNumber = i
                students[i] = student
            }
//            var student = Student()
////                student.id = i
//            student.studentNumber = 0
            InsertAsyncTask(studentDao!!).execute(*students)
//            InsertAsyncTask()
        }
        buttonClear.setOnClickListener {
            DeleteAsyncTask(studentDao!!).execute()
        }
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
    }
    inner class InsertAsyncTask(studentDao:StudentDao): AsyncTask<Student, Void, Void>() {
        var mStudentDao:StudentDao = studentDao
        override fun doInBackground(vararg params: Student?): Void? {
            if (params!=null){
                mStudentDao.insertStudent(*params as Array<out Student>)
            }else{
                Log.i("aaaa","bbbbbbbbbbbbbbbbbbbbbb")
            }

            return null
        }

    }
    inner class DeleteAsyncTask(studentDao:StudentDao): AsyncTask<Void, Void, Void>() {
        var mStudentDao:StudentDao = studentDao
        override fun doInBackground(vararg params: Void?): Void? {
            mStudentDao.deleteAllStudents()
            return null
        }

    }
}