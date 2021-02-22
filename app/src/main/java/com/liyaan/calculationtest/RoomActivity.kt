package com.liyaan.calculationtest

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.liyaan.calculationtest.room.WordDao
import com.liyaan.calculationtest.room.WordDataBase
import com.liyaan.calculationtest.room.WordEntity

class RoomActivity:AppCompatActivity() {
    var wordDataBase:WordDataBase? = null
    var wordDao:WordDao? = null
    lateinit var textView: TextView
    var button10:Button? = null
    var button17:Button? = null
    var button15:Button? = null
    var button16:Button? = null
    var button18:Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        textView = findViewById(R.id.textView15)
        wordDataBase = Room.databaseBuilder(this,WordDataBase::class.java,"word_database").allowMainThreadQueries()
                .build()
        wordDao = wordDataBase!!.getWordDao()
        button10 = findViewById(R.id.button10)
        button17 = findViewById(R.id.button17)
        button15 = findViewById(R.id.button15)
        button16 = findViewById(R.id.button16)
        button18 = findViewById(R.id.button18)
        button10!!.setOnClickListener {
            val word1 = WordEntity("aaaaaaa","小学生的")
            val word2 = WordEntity("aaaaaaa1","小学生的a")
            val word3 = WordEntity("aaaaaaa1","小学生的")
            wordDao!!.insertWords(word1,word2,word3)
        }
        button17!!.setOnClickListener {
            selectAll()
        }
        button15!!.setOnClickListener {
            wordDao!!.deleteAllWords()
            selectAll()
        }
        button16!!.setOnClickListener {
            val word3 = WordEntity("bbbbbbb","ddfsdfsdf")
            word3.id = 12
            wordDao!!.updateWords(word3)
            selectAll()
        }
        button18!!.setOnClickListener {
            val word3 = WordEntity("bbbbbbb","ddfsdfsdf")
            word3.id = 10
            wordDao!!.deleteWords(word3)
            selectAll()
        }
    }
    fun selectAll(){
        val list = wordDao!!.getAllWords()
        var test:String = ""
        if (list.isNotEmpty()){
            list.forEach {
                test += "${it.id}  ${it.name}   ${it.password}\n"
            }
            textView.text = test
        }else{
            textView.text = ""
        }

    }
}