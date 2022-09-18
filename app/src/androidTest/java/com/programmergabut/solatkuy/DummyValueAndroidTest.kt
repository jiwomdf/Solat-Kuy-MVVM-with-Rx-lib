package com.programmergabut.solatkuy

import com.programmergabut.solatkuy.data.local.localentity.*
import com.programmergabut.solatkuy.data.remote.json.compassJson.CompassResponse
import com.programmergabut.solatkuy.data.remote.json.prayerJson.PrayerResponse
import com.programmergabut.solatkuy.data.remote.json.quranallsurahJson.AllSurahResponse
import com.programmergabut.solatkuy.data.remote.json.readsurahJsonAr.ReadSurahArResponse
import com.programmergabut.solatkuy.data.remote.json.readsurahJsonEn.ReadSurahEnResponse
import com.programmergabut.solatkuy.util.Constant

object DummyValueAndroidTest {

    /* Last update : 4 Jan 2021 */
    val ALL_SURAH_SERVICE_JSON = "allSurahService.json"
    val CALENDAR_API_SERVICE_JSON = "calendarApiService.json"
    val QIBLA_API_SERVICE_JSON = "qiblaApiService.json"
    val READ_SURAH_EN_SERVICE_JSON = "readSurahEnService.json"
    val READ_SURAH_SERVICE_JSON = "readSurahService.json"
    val METHOD_SERVICE_JSON = "methodService.json"

    /* Remote */
    fun getNotifiedPrayer(): List<MsNotifiedPrayer> {
        val listNotifiedPrayer = mutableListOf<MsNotifiedPrayer>()

        listNotifiedPrayer.add(
            MsNotifiedPrayer(
                Constant.FAJR,
                true,
                Constant.FAJR_TIME
            )
        )
        listNotifiedPrayer.add(
            MsNotifiedPrayer(
                Constant.DHUHR,
                true,
                Constant.DHUHR_TIME
            )
        )
        listNotifiedPrayer.add(
            MsNotifiedPrayer(
                Constant.ASR,
                true,
                Constant.ASR_TIME
            )
        )
        listNotifiedPrayer.add(
            MsNotifiedPrayer(
                Constant.MAGHRIB,
                true,
                Constant.MAGHRIB_TIME
            )
        )
        listNotifiedPrayer.add(
            MsNotifiedPrayer(
                Constant.ISHA,
                true,
                Constant.ISHA_TIME
            )
        )
        listNotifiedPrayer.add(
            MsNotifiedPrayer(
                Constant.SUNRISE,
                true,
                Constant.SUNRISE_TIME
            )
        )


        return listNotifiedPrayer
    }

    inline fun <reified BASE> surahEnID_1(): ReadSurahEnResponse{
        return JsonToPojoConverterAndroidTest.convertJson<BASE, ReadSurahEnResponse>(READ_SURAH_EN_SERVICE_JSON)
    }

    inline fun <reified BASE> surahArID_1(): ReadSurahArResponse{
        return JsonToPojoConverterAndroidTest.convertJson<BASE, ReadSurahArResponse>(READ_SURAH_SERVICE_JSON)
    }

    inline fun <reified BASE> fetchPrayerApi(): PrayerResponse{
        return JsonToPojoConverterAndroidTest.convertJson<BASE, PrayerResponse>(CALENDAR_API_SERVICE_JSON)
    }

    inline fun <reified BASE> fetchCompassApi(): CompassResponse{
        return JsonToPojoConverterAndroidTest.convertJson<BASE, CompassResponse>(QIBLA_API_SERVICE_JSON)
    }

    inline fun <reified BASE> fetchAllSurah(): AllSurahResponse{
        return JsonToPojoConverterAndroidTest.convertJson<BASE, AllSurahResponse>(ALL_SURAH_SERVICE_JSON)
    }

    /*inline fun <reified BASE> fetchMethod(): MethodResponse{
        return JsonToPojoConverterAndroidTest.convertJson<BASE, MethodResponse>(METHOD_SERVICE_JSON)
    }*/

    /* Database */
    fun getMsConfiguration(): MsConfiguration {
        return MsConfiguration(0, "-7.5633548", "110.8041806", "11","7","2020")
    }

    fun getListMsFavSurah(): MutableList<MsFavSurah> {
        return mutableListOf()
    }

    fun getMsSetting(): MsSetting {
        return MsSetting(1, true)
    }

    fun getMsCalculationMethods(): List<MsCalculationMethods> {
        return listOf(
            MsCalculationMethods(1,"Muslim World League",1),
            MsCalculationMethods(2,"Islamic Society of North America (ISNA)",2),
            MsCalculationMethods(3,"Egyptian General Authority of Survey",3),
            MsCalculationMethods(4,"Umm Al-Qura University, Makkah",4),
            MsCalculationMethods(5,"University of Islamic Sciences, Karachi",5),
            MsCalculationMethods(6,"Institute of Geophysics, University of Tehran",6),
            MsCalculationMethods(7,"Shia Ithna-Ashari, Leva Institute, Qum",7),
            MsCalculationMethods(8,"Gulf Region",8),
            MsCalculationMethods(9,"Kuwait",9),
            MsCalculationMethods(10,"Qatar",10),
            MsCalculationMethods(11,"Majlis Ugama Islam Singapura, Singapore",11),
            MsCalculationMethods(12,"Union Organization Islamic de France",12),
            MsCalculationMethods(13,"Moonsighting Committee Worldwide (Moonsighting.com)", 13)
        )
    }

}