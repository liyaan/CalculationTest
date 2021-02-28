package com.liyaan.calculationtest.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    var wordRepostory:WordRepostory = WordRepostory.getDataBase(application)

    fun insertWord(vararg words:WordEntity){
        wordRepostory.insertWord(*words)
    }
    fun updateWord(vararg words:WordEntity){
        wordRepostory.updateWord(*words)
//        UpdateAsyncTask(wordDao!!).execute(*words)
    }
    fun deleteWord(vararg words:WordEntity){
        wordRepostory.deleteWord(*words)
//        DeleteAsyncTask(wordDao!!).execute(*words)
    }
    fun deleteAllWord(){
        wordRepostory.deleteAllWord()
//        DeleteAllAsyncTask(wordDao!!).execute()
    }
    fun findWordsWithPattern(pattern:String): LiveData<MutableList<WordEntity>> {
       return wordRepostory.findWordsWithPattern(pattern)
    }
    fun getAllWords():LiveData<MutableList<WordEntity>>{
        return wordRepostory.getAllWords()
    }
}