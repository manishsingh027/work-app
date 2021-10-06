package com.example.workmanager

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FilteringWorker(context: Context,params : WorkerParameters ) : Worker(context,params) {

    override fun doWork(): Result {
        try {

            for(i in 0..6000000) {
                Log.i("MyTag", "Filtering work ---> $i ")
            }
            return Result.success()
        }
        catch (e: Exception) {
            return Result.failure()
        }
    }
}