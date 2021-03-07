package com.liyaan.calculationtest.gallery.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(context:Context){
    companion object{
        private var INSATNCE:VolleySingleton? = null
        fun getInsatnce(context:Context)=
                INSATNCE?: synchronized(this){
                    VolleySingleton(context).also { INSATNCE = it }
                }
    }
    val requestQueue:RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
}