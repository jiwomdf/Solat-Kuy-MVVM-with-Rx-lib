package com.programmergabut.solatkuy.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.programmergabut.solatkuy.data.local.dao.MsApi1Dao
import com.programmergabut.solatkuy.data.local.dao.NotifiedPrayerDao
import com.programmergabut.solatkuy.ui.LocationHelper
import com.programmergabut.solatkuy.ui.PushNotificationHelper
import javax.inject.Inject

class FireAlarmManagerWorker(
    val context: Context,
    workerParameters: WorkerParameters,
    private val notifiedPrayerDao: NotifiedPrayerDao,
    private val msApi1Dao: MsApi1Dao
): Worker(context, workerParameters) {

    private val TAG = "FireAlarmManagerWorker"

    companion object {
        const val UNIQUE_KEY = "fire_alarm_manager_unique_key"
    }

    override fun doWork(): Result {
        return try {
            val api1 = msApi1Dao.getMsApi1()
            val cityName = if(api1?.latitude == null || api1?.longitude == null){
                "-"
            } else {
                LocationHelper.getCity(context, api1.latitude.toDouble(), api1.longitude.toDouble()) ?: "-"
            }
            val listPrayer = notifiedPrayerDao.getListNotifiedPrayerSync()

            PushNotificationHelper(context, listPrayer, cityName)

            Log.e(TAG, cityName)
            Log.e(TAG, listPrayer.toString())

            Result.success()
        }catch (ex: Exception){
            Log.e(TAG, ex.message.toString())
            Result.failure()
        }
    }



}