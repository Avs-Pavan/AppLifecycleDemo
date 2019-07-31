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
        Log.e("on onEnterForeground",getTime())
        RxBus.publish(RxEvent.EventAddPerson("1"))
        enterForegroundToast.showAfterCanceling(enterBackgroundToast)

    }

    /**
     * Shows background {@link android.widget.Toast} after attempting to cancel the foreground one.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        RxBus.publish(RxEvent.EventAddPerson("2"))
        Log.e("on onEnterBackground",getTime())
        enterBackgroundToast.showAfterCanceling(enterForegroundToast)
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
}
