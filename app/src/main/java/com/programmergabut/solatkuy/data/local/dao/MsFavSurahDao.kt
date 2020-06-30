package com.programmergabut.solatkuy.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.programmergabut.solatkuy.data.local.localentity.MsFavSurah

@Dao
interface MsFavSurahDao {

    @Query("select * from MsFavSurah")
    fun getListFavSurah(): LiveData<List<MsFavSurah>>

    @Query("select * from MsFavSurah where surahID like :surahID")
    fun getFavSurahBySurahID(surahID: Int): LiveData<MsFavSurah>

    @Query("delete from MsFavSurah")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMsFavSurah(msFavSurah: MsFavSurah)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMsSurah(msFavSurah: MsFavSurah)

}