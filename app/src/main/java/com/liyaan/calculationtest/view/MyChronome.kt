package com.liyaan.calculationtest.view

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

class MyChronome(context: Context?, attrs: AttributeSet?) : Chronometer(context, attrs),LifecycleObserver {

    var elaTime:Long = 0
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseMeter(){
        elaTime = SystemClock.elapsedRealtime() - base
        stop()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeMeter(){
        base = SystemClock.elapsedRealtime() - elaTime
        start()
    }
}