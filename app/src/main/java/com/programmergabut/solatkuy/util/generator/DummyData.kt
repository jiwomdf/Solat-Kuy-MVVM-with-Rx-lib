package com.programmergabut.solatkuy.util.generator

import com.programmergabut.solatkuy.data.local.localentity.MsApi1
import com.programmergabut.solatkuy.data.local.localentity.NotifiedPrayer
import com.programmergabut.solatkuy.data.remote.remoteentity.asmaalhusnaJson.AsmaAlHusnaApi
import com.programmergabut.solatkuy.data.remote.remoteentity.asmaalhusnaJson.Data
import com.programmergabut.solatkuy.data.remote.remoteentity.asmaalhusnaJson.En
import com.programmergabut.solatkuy.data.remote.remoteentity.compassJson.CompassApi
import com.programmergabut.solatkuy.data.remote.remoteentity.prayerJson.Date
import com.programmergabut.solatkuy.data.remote.remoteentity.prayerJson.PrayerApi
import com.programmergabut.solatkuy.data.remote.remoteentity.prayerJson.Timings
import com.programmergabut.solatkuy.data.remote.remoteentity.readsurahJsonEn.Ayah
import com.programmergabut.solatkuy.data.remote.remoteentity.readsurahJsonEn.Edition
import com.programmergabut.solatkuy.data.remote.remoteentity.readsurahJsonEn.ReadSurahEnApi
import com.programmergabut.solatkuy.util.enumclass.EnumConfig

object DummyData {

    fun getNotifiedPrayer(): List<NotifiedPrayer> {
        val listNotifiedPrayer = mutableListOf<NotifiedPrayer>()

        listNotifiedPrayer.add(
            NotifiedPrayer(
                EnumConfig.fajr,
                true,
                "00:00"
            )
        )
        listNotifiedPrayer.add(
            NotifiedPrayer(
                EnumConfig.dhuhr,
                true,
                "00:00"
            )
        )
        listNotifiedPrayer.add(
            NotifiedPrayer(
                EnumConfig.asr,
                true,
                "00:00"
            )
        )
        listNotifiedPrayer.add(
            NotifiedPrayer(
                EnumConfig.maghrib,
                true,
                "00:00"
            )
        )
        listNotifiedPrayer.add(
            NotifiedPrayer(
                EnumConfig.isha,
                true,
                "00:00"
            )
        )
        listNotifiedPrayer.add(
            NotifiedPrayer(
                EnumConfig.sunrise,
                true,
                "00:00"
            )
        )


        return listNotifiedPrayer
    }

    fun fetchAsmaAlHusnaApi(): AsmaAlHusnaApi {

        val list = mutableListOf<Data>()

        list.add(Data(En("test"),"test",0,"test"))

        return AsmaAlHusnaApi(0, list, "testing")
    }

    fun fetchSurahApi(): ReadSurahEnApi{
        val listAyah = mutableListOf<Ayah>()
        listAyah.add(Ayah(0,0,0,0,0,0,0,false,"test"))
        return ReadSurahEnApi(0,
            com.programmergabut.solatkuy.data.remote.remoteentity.readsurahJsonEn.Data(listAyah,
                Edition("","","","","","",""),
                "", "", "", 0,0, ""
            )
            ,"")
    }

    fun fetchPrayerApi(): PrayerApi{
        val listData = mutableListOf<com.programmergabut.solatkuy.data.remote.remoteentity.prayerJson.Data>()
        listData.add(com.programmergabut.solatkuy.data.remote.remoteentity.prayerJson.Data(
            Date(null, null, null, null),null,
            Timings("", "", "", "", "", "", "", "", "")))

        return PrayerApi(0, listData,"testing")
    }

    fun fetchCompassApi(): CompassApi{
        return CompassApi(0,com.programmergabut.solatkuy.data.remote.remoteentity.compassJson.Data(0.0,0.0,0.0),"testing")
    }

    fun getMsApi1(): MsApi1 {
        return MsApi1(0, "","","","", "")
    }

}