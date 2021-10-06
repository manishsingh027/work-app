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

class UploadWorkerClass(context: Context,params : WorkerParameters ) : Worker(context,params) {

    override fun doWork(): Result {
        try {
            val count = inputData.getInt("KEY_COUNT_VALUE",0)

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate : String = time.format(Date())
            val outputData = Data.Builder()
                .putString("KEY_WORKER",currentDate)
                .build()
            for(i in 0..count) {
                Log.i("MyTag", i.toString())
            }
            return Result.success(outputData)
        }
        catch (e: Exception) {
            return Result.failure()
        }
    }
}