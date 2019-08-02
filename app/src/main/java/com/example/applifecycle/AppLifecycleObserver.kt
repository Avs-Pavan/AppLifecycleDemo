package com.example.applifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import therajanmaurya.rxbus.kotlin.RxBus
import therajanmaurya.rxbus.kotlin.RxEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Displays a message when app comes to foreground and goes to background.
 */
class AppLifecycleObserver @Inject constructor(context: Context) : LifecycleObserver {

    private val enterForegroundToast =
            Toast.makeText(context, context.getString(R.string.foreground_message), Toast.LENGTH_SHORT)

    private val enterBackgroundToast =
            Toast.makeText(context, context.getString(R.string.background_message), Toast.LENGTH_SHORT)

    /**
     * Shows foreground {@link android.widget.Toast} after attempting to cancel the background one.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
         //       WindowManager.LayoutParams.FLAG_SECURE)
        Log.e(" ON_START",getTime())
        RxBus.publish(RxEvent.EventAddPerson("1"))
//        enterForegroundToast.showAfterCanceling(enterBackgroundToast)

    }

    /**
     * Shows background {@link android.widget.Toast} after attempting to cancel the foreground one.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        RxBus.publish(RxEvent.EventAddPerson("2"))
        Log.e(" ON_STOP",getTime())
       // enterBackgroundToast.showAfterCanceling(enterForegroundToast)
    }

    private fun Toast.showAfterCanceling(toastToCancel: Toast) {
        toastToCancel.cancel()
        this.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted =current.format(formatter)
        return formatted.toString()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onpause() {
        Log.e(" ON_PAUSE",getTime())

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun oncreate() {
        Log.e(" ON_CREATE",getTime())
        //enterBackgroundToast.showAfterCanceling(enterForegroundToast)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onany() {
        Log.e(" ON_ANY",getTime())
        //enterBackgroundToast.showAfterCanceling(enterForegroundToast)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun ones() {
        Log.e(" ON_RESUME",getTime())
       // enterBackgroundToast.showAfterCanceling(enterForegroundToast)
    }
}
