package com.liyaan.calculationtest.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import java.util.*

class MyViewModel(application: Application,handle:SavedStateHandle) : AndroidViewModel(application) {
    lateinit var handle:SavedStateHandle
    val mApplication: Application = application
    private val KEY_HIGH_SCORE = "key_high_score"
    private val KEY_LEFT_NUMBER = "key_left_number"
    private val KEY_RIGHT_NUMBER = "key_right_number"
    private val KEY_OPERATOR = "key_operator"
    private val KEY_ANSWER = "key_answer"

    private val SAVE_SHP_DATA_NAME = "save_shp_data_name"
    private val KEY_CURRENT_SCORE = "key_current_score"

    private val LEVEL= 100
    var GO_TO_TIME:Long = 0
    var win_flag:Boolean = false
    init {
        if (!handle.contains(KEY_HIGH_SCORE)){
            val shp = application.getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
            handle.set(KEY_HIGH_SCORE,shp.getInt(KEY_HIGH_SCORE,0))
            handle.set(KEY_LEFT_NUMBER,0)
            handle.set(KEY_RIGHT_NUMBER,0)
            handle.set(KEY_OPERATOR,"+")
            handle.set(KEY_ANSWER,0)
            handle.set(KEY_CURRENT_SCORE,0)
//            handle.set(GO_TO_TIME,0)
        }
        this.handle = handle
    }
    fun getHighScore():MutableLiveData<Int>{
        return handle.getLiveData(KEY_HIGH_SCORE)
    }
    fun getLeftNumber():MutableLiveData<Int>{
        return handle.getLiveData(KEY_LEFT_NUMBER)
    }
    fun getRightNumber():MutableLiveData<Int>{
        return handle.getLiveData(KEY_RIGHT_NUMBER)
    }
    fun getOperator():MutableLiveData<String>{
        return handle.getLiveData(KEY_OPERATOR)
    }
    fun getAnswer():MutableLiveData<Int>{
        return handle.getLiveData(KEY_ANSWER)
    }
    fun getCurrentScore():MutableLiveData<Int>{
        return handle.getLiveData(KEY_CURRENT_SCORE)
    }

    fun generator(){
        val random = Random()
        val x:Int = random.nextInt(LEVEL)+1
        val y :Int = random.nextInt(LEVEL)+1
        if (x%2==0){
            getOperator().value = "+"
            if (x>y){
                getAnswer().value = x
                getLeftNumber().value = x-y
                getRightNumber().value = y
            }else{
                getAnswer().value = y
                getLeftNumber().value = y-x
                getRightNumber().value = x
            }
        }else{
            getOperator().value = "-"
            if (x>y){
                getAnswer().value = x-y
                getLeftNumber().value = x
                getRightNumber().value = y
            }else{
                getAnswer().value = y-x
                getLeftNumber().value = y
                getRightNumber().value = x
            }
        }
    }

    fun save(){
        val shp = mApplication.getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
        val editor = shp.edit()
        editor.putInt(KEY_HIGH_SCORE,getHighScore().value!!)
        editor.apply()
    }

    fun answerCount(){
        getCurrentScore().value = getCurrentScore().value!!+1
        if (getCurrentScore().value!!>getHighScore().value!!){
            getHighScore().value = getCurrentScore().value!!
            win_flag = true
        }
        generator()
    }
}