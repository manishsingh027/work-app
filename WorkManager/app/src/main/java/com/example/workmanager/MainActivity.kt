package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workManager = WorkManager.getInstance(this)
        findViewById<Button>(R.id.button).setOnClickListener {
//            setOneTimeWorkRequest()
            setPeriodicWorkRequest()
        }
    }

    fun setOneTimeWorkRequest() {
//        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(UploadWorkerClass::class.java).build()
        val data : Data = Data.Builder()
                                .putInt("KEY_COUNT_VALUE",125)
                                .build()

        val constraints = Constraints.Builder()
                                    .setRequiresCharging(true)
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build()

        val uploadRequest = OneTimeWorkRequestBuilder<UploadWorkerClass>()
                                                    .setConstraints(constraints)
                                                    .setInputData(data)
                                                    .build()
        val filteringWorkerRequest = OneTimeWorkRequestBuilder<FilteringWorker>()
                                                    .build()
        val compressingWorkerRequest = OneTimeWorkRequestBuilder<CompressingWorker>()
                                                    .build()
        val downloadingWorkerRequest = OneTimeWorkRequestBuilder<DownloadingWorker>()
                                                    .build()
        var parallelWorker = mutableListOf<OneTimeWorkRequest>()
        parallelWorker.add(downloadingWorkerRequest)
        parallelWorker.add(compressingWorkerRequest)


//        workManager
//            .beginWith(filteringWorkerRequest)
//            .then(compressingWorkerRequest)
//            .then(uploadRequest)
//            .enqueue()
        workManager
            .beginWith(parallelWorker)
            .then(uploadRequest)
            .enqueue()
            workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(this, Observer {
                findViewById<TextView>(R.id.textView).text = it.state.toString()
                if(it.state.isFinished) {
                    val data : Data = it.outputData
                    val message : String? = data.getString("KEY_WORKER")
                    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
                }
            })

    }
    fun setPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<DownloadingWorker>(16,TimeUnit.MINUTES).build()
        workManager.enqueue(periodicWorkRequest)
    }
}