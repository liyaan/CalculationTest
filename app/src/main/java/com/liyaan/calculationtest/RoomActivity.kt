package com.liyaan.calculationtest

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liyaan.calculationtest.room.MyAdapter
import com.liyaan.calculationtest.room.RoomViewModel
import com.liyaan.calculationtest.room.WordEntity

class RoomActivity:AppCompatActivity() {

    var button10:Button? = null
    var button17:Button? = null
    var button16:Button? = null
    var button18:Button? = null
    var mRoomViewModel:RoomViewModel? = null
    var mRecyclerView: RecyclerView? = null
    var mAdapter:MyAdapter? = null
    var mSwitch: Switch? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        mRoomViewModel = ViewModelProviders.of(this).get(RoomViewModel::class.java)
        mSwitch = findViewById(R.id.switch1)
        mRecyclerView = findViewById(R.id.recyclerView)
        mAdapter = MyAdapter(mRoomViewModel!!)

        mRecyclerView!!.layoutManager =LinearLayoutManager(this)
        mRecyclerView!!.adapter = mAdapter
        mSwitch!!.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked){
                    mAdapter!!.id = 1
                }else{
                    mAdapter!!.id = 0
                }
                mRecyclerView!!.adapter = mAdapter
            }

        })
        mRoomViewModel!!.wordRepostory.liveDataList.observe(this, Observer<MutableList<WordEntity>> { list ->
//            var test:StringBuilder = StringBuilder()
//            if (list!!.isNotEmpty()){
//                list!!.forEach {
//                    test.append("${it.id}  ${it.name}   ${it.password}\n")
//                }
//                textView.text = test.toString()
//            }else{
//                textView.text = ""
//            }
            val tempSize = mAdapter!!.itemCount
            mAdapter!!.list = list
            if (tempSize!=list.size){
                mAdapter!!.notifyDataSetChanged()
            }
        })
        button10 = findViewById(R.id.button10)
        button17 = findViewById(R.id.button17)

        button16 = findViewById(R.id.button16)
        button18 = findViewById(R.id.button18)
        button10!!.setOnClickListener {
            val english = arrayOf(
            "Hello",
            "World",
            "Android",
            "Google",
            "Studio",
            "Project",
            "Database",
            "Recycler",
            "View",
            "String",
            "Value",
            "Integer"
            )
            val chinese =arrayOf(
            "你好",
            "世界",
            "安卓系统",
            "谷歌公司",
            "工作室",
            "项目",
            "数据库",
            "回收站",
            "视图",
            "字符串",
            "价值",
            "整数类型"
            )
            english.forEachIndexed { index, s ->
                val word3 = WordEntity(s,chinese[index])
                mRoomViewModel!!.insertWord(word3)
            }


//            InsertAsyncTask(wordDao!!).execute(word1,word2,word3)
//            wordDao!!.insertWords(word1,word2,word3)
        }
        button17!!.setOnClickListener {

        }
//        button15!!.setOnClickListener {
//            mRoomViewModel!!.deleteAllWord()
//            DeleteAllAsyncTask(wordDao!!).execute()
//            wordDao!!.deleteAllWords()
//        }
        button16!!.setOnClickListener {
            val word3 = WordEntity("bbbbbbb","大家好11")
            word3.id = 124
            mRoomViewModel!!.updateWord(word3)
//            UpdateAsyncTask(wordDao!!).execute(word3)
//            wordDao!!.updateWords(word3)
        }
        button18!!.setOnClickListener {
            val word3 = WordEntity("bbbbbbb","今天天气")
            word3.id = 126
            mRoomViewModel!!.deleteWord(word3)
//            DeleteAsyncTask(wordDao!!).execute(word3)
//            wordDao!!.deleteWords(word3)
        }
    }


}