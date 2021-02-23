package com.liyaan.calculationtest.room

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room

class WordRepostory(context: Context) {
    private val mContext = context
    private var wordDao:WordDao? = null
    var liveDataList: LiveData<MutableList<WordEntity>>

    init {
        val wordDataBase:WordDataBase = WordDataBase.getDataBase(mContext.applicationContext)
        wordDao = wordDataBase.getWordDao()
        liveDataList = wordDao!!.getAllWords()
    }

    fun insertWord(vararg words:WordEntity){
        InsertAsyncTask(wordDao!!).execute(*words)
    }
    fun updateWord(vararg words:WordEntity){
        UpdateAsyncTask(wordDao!!).execute(*words)
    }
    fun deleteWord(vararg words:WordEntity){
        DeleteAsyncTask(wordDao!!).execute(*words)
    }
    fun deleteAllWord(){
        DeleteAllAsyncTask(wordDao!!).execute()
    }
    inner class InsertAsyncTask(wordDao: WordDao): AsyncTask<WordEntity, Void, Void>(){
        val wordDao: WordDao = wordDao
        override fun doInBackground(vararg params: WordEntity?): Void? {
            wordDao.insertWords(*params as Array<out WordEntity>)
            return null
        }
    }
    inner class UpdateAsyncTask(wordDao: WordDao): AsyncTask<WordEntity, Void, Void>(){
        val wordDao: WordDao = wordDao
        override fun doInBackground(vararg params: WordEntity?): Void? {
            wordDao.updateWords(*params as Array<out WordEntity>)
            return null
        }
    }

    inner class DeleteAsyncTask(wordDao: WordDao): AsyncTask<WordEntity, Void, Void>(){
        val wordDao: WordDao = wordDao
        override fun doInBackground(vararg params: WordEntity?): Void? {
            wordDao.deleteWords(*params as Array<out WordEntity>)
            return null
        }
    }
    inner class DeleteAllAsyncTask(wordDao: WordDao): AsyncTask<Void, Void, Void>(){
        val wordDao: WordDao = wordDao
        override fun doInBackground(vararg params: Void?): Void? {
            wordDao.deleteAllWords()
            return null
        }
    }
    companion object{
        var INSTANCE:WordRepostory? = null
        @JvmStatic
        @Synchronized
        fun getDataBase(context: Context):WordRepostory{
            if (INSTANCE==null){
                INSTANCE =WordRepostory(context)
            }
            return INSTANCE!!
        }
    }
}