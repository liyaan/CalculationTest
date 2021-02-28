package com.liyaan.calculationtest

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity:AppCompatActivity() {

    var navController:NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
         navController = Navigation.findNavController(findViewById(R.id.fragment2))
         NavigationUI.setupActionBarWithNavController(this,navController!!)
//        mSwitch!!.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
//            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//                if (isChecked){
//                    mAdapter!!.id = 1
//                }else{
//                    mAdapter!!.id = 0
//                }
//                mRecyclerView!!.adapter = mAdapter
//            }
//
//        })

//        button10!!.setOnClickListener {
//            val english = arrayOf(
//            "Hello",
//            "World",
//            "Android",
//            "Google",
//            "Studio",
//            "Project",
//            "Database",
//            "Recycler",
//            "View",
//            "String",
//            "Value",
//            "Integer"
//            )
//            val chinese =arrayOf(
//            "你好",
//            "世界",
//            "安卓系统",
//            "谷歌公司",
//            "工作室",
//            "项目",
//            "数据库",
//            "回收站",
//            "视图",
//            "字符串",
//            "价值",
//            "整数类型"
//            )
//            english.forEachIndexed { index, s ->
//                val word3 = WordEntity(s,chinese[index])
//                mRoomViewModel!!.insertWord(word3)
//            }
//
//
////            InsertAsyncTask(wordDao!!).execute(word1,word2,word3)
////            wordDao!!.insertWords(word1,word2,word3)
//        }

//        button15!!.setOnClickListener {
//            mRoomViewModel!!.deleteAllWord()
//            DeleteAllAsyncTask(wordDao!!).execute()
//            wordDao!!.deleteAllWords()
//        }

    }



    override fun onBackPressed() {
        super.onBackPressed()

        navController!!.navigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        navController!!.navigateUp()
        val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mian_layout.windowToken,0)
        return super.onSupportNavigateUp()
    }
}