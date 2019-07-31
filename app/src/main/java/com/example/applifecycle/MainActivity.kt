package com.example.applifecycle

import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import io.reactivex.disposables.Disposable
import therajanmaurya.rxbus.kotlin.RxBus
import therajanmaurya.rxbus.kotlin.RxEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    private lateinit var personDisposable: Disposable
    lateinit var mask: LinearLayout
    val REQUEST_CODE = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mask=findViewById(R.id.mask)
        personDisposable = RxBus.listen(RxEvent.EventAddPerson::class.java).subscribe {
            Log.e("type", it.personName + " ------ " + getTime())
            //bg 2 fore 1
            mask.visibility = if (it.personName.equals("1")){
                View.GONE
            } else{
                View.VISIBLE
            }

        }
    }

    fun req(view: View) {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Log.e("permission ", "denied")
                else
                    Log.e("permission ", "granted")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        Log.e("on pause", getTime())
        //mask.visibility=View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
       // mask.visibility=View.GONE
        Log.e("on resume", getTime())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        return formatted.toString()
    }
}
