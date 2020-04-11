package com.programmergabut.solatkuy.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import com.programmergabut.solatkuy.data.model.entity.PrayerLocal
import com.programmergabut.solatkuy.broadcaster.PrayerBroadcastReceiver
import com.programmergabut.solatkuy.data.model.prayerJson.Timings
import java.util.*

/*
 * Created by Katili Jiwo Adi Wiyono on 02/04/20.
 */

class PushNotificationHelper(context: Context, selList: MutableList<PrayerLocal>, mCityName: String): ContextWrapper(context) {

    private var mCityName: String? = null

    init {
        this.mCityName = mCityName

        val intent = Intent(context, PrayerBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val listPrayerBundle = bundleCreator(selList)

        selList.sortBy { x -> x.prayerID }

        val selPrayer = SelectPrayerHelper.selectNextPrayerToLocalPrayer(selList)
        //selPrayer = PrayerLocal(3,"mantap 3",true,"22:12") #testing purpose

        selPrayer?.let{

            val hour = it.prayerTime.split(":")[0].trim()
            val minute = it.prayerTime.split(":")[1].split(" ")[0].trim()

            val c = Calendar.getInstance()
            c.set(Calendar.HOUR_OF_DAY, hour.toInt())
            c.set(Calendar.MINUTE, minute.toInt())
            c.set(Calendar.SECOND, 0)

            intent.putExtra("prayer_id", it.prayerID)
            intent.putExtra("prayer_name", it.prayerName)
            intent.putExtra("prayer_time", it.prayerTime)
            intent.putExtra("prayer_city", mCityName)
            intent.putExtra("list_prayer_bundle", listPrayerBundle)

            val pendingIntent = PendingIntent.getBroadcast(context, 400, intent, 0)

            if(c.before(Calendar.getInstance()))
                c.add(Calendar.DATE, 1)

            if(it.isNotified)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
            else
                alarmManager.cancel(pendingIntent)
        }

    }

    private fun bundleCreator(selList: MutableList<PrayerLocal>): Bundle {

        val listPID = arrayListOf<Int>()
        val listPName = arrayListOf<String>()
        val listPTime = arrayListOf<String>()
        val listPIsNotified = arrayListOf<Int>()
        val listPCity = arrayListOf<String>()

        selList.forEach {
            listPID.add(it.prayerID)
            listPName.add(it.prayerName)
            listPTime.add(it.prayerTime)

            if(it.isNotified)
                listPIsNotified.add(1)
            else
                listPIsNotified.add(0)

            if(mCityName.isNullOrEmpty())
                listPCity.add("-")
            else
                listPCity.add(mCityName!!)
        }

        val b = Bundle()
        b.putIntegerArrayList("list_PID", listPID)
        b.putStringArrayList("list_PName", listPName)
        b.putStringArrayList("list_PTime", listPTime)
        b.putIntegerArrayList("list_PIsNotified", listPIsNotified)
        b.putStringArrayList("list_PCity", listPCity)

        return b
    }

}