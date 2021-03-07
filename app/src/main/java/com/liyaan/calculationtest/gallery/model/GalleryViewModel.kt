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

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<MutableList<PhotoItem>>()
    val photoListLive :LiveData<MutableList<PhotoItem>>
    get() = _photoListLive

    fun getData(){
        val stringRequest = StringRequest(Request.Method.GET,getUrl(),
            Response.Listener {
                Log.i("aaaa",it)
                if (it.isNotEmpty()){
                    _photoListLive.value =  Gson().fromJson(it,Paxabay::class.java).hits
                }
            },
            Response.ErrorListener {
                Log.d("warm",it.toString())
            })
        VolleySingleton.getInsatnce(getApplication()).requestQueue.add(stringRequest)
    }
    private fun getUrl():String{
        return "https://pixabay.com/api/?key=20565909-8dffa9b1a26bac15c2a74f72d&q=${keyWords.random()}&per_page=100"
    }
    private val keyWords = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal")
}