package com.liyaan.calculationtest.gallery.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.liyaan.calculationtest.gallery.bean.Paxabay
import com.liyaan.calculationtest.gallery.bean.PhotoItem
import com.liyaan.calculationtest.gallery.volley.VolleySingleton
import kotlin.math.ceil

const val DATA_STATUS_CAN_LOAD_MORE = 0
const val DATA_STATUS_NO_MORE = 1
const val DATA_STATUS_NETWORK_ERROR = 2

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _dataStatusLive = MutableLiveData<Int>()
    val dataStatusLive:LiveData<Int> get() = _dataStatusLive
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive :LiveData<List<PhotoItem>>
    get() = _photoListLive
    var needToScrollTop = true
    private val keyWords = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal")
    private val perPage = 100

    private var currentPage = 1
    private var totalPage= 1
    private var currentKey = "cat"
    private var isNewQuery = true
    private var isLoading = false

    init {
        resetQuery()
    }

    fun resetQuery(){
        currentPage = 1
        totalPage = 1
        currentKey = keyWords.random()
        isNewQuery = true
        needToScrollTop = true
        getData()
    }
    fun getData(){
        if (isLoading) return
        if (currentPage>totalPage) {
            _dataStatusLive.value = DATA_STATUS_NO_MORE
            return
        }
        isLoading = true
        val stringRequest = StringRequest(Request.Method.GET,getUrl(),
            Response.Listener {
                Log.i("aaaa",it)
                if (it.isNotEmpty()){
                    with(Gson().fromJson(it,Paxabay::class.java)){
                        totalPage = ceil(totalHits.toDouble()/perPage).toInt()
                        if (isNewQuery){
                            _photoListLive.value = hits
                        }else{
                            _photoListLive.value = arrayListOf(_photoListLive.value!!,hits).flatten()
                        }
                    }
                    _dataStatusLive.value = DATA_STATUS_CAN_LOAD_MORE
                    isLoading = false
                    isNewQuery = false
                    currentPage++
//                    _photoListLive.value =  Gson().fromJson(it,Paxabay::class.java).hits
                }
            },
            Response.ErrorListener {
                isLoading = false
                _dataStatusLive.value = DATA_STATUS_NETWORK_ERROR
            })
        VolleySingleton.getInsatnce(getApplication()).requestQueue.add(stringRequest)
    }
    private fun getUrl():String{
        return "https://pixabay.com/api/?key=20565909-8dffa9b1a26bac15c2a74f72d&q=${currentKey}" +
                "&per_page=$perPage&page=$currentPage"
    }

}