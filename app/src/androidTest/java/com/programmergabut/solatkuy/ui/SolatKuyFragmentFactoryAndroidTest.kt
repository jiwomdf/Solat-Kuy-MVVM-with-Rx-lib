package com.programmergabut.solatkuy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.programmergabut.solatkuy.data.FakePrayerRepositoryAndroidTest
import com.programmergabut.solatkuy.data.FakeQuranRepositoryAndroidTest
import com.programmergabut.solatkuy.ui.main.qibla.CompassFragment
import com.programmergabut.solatkuy.ui.main.qibla.CompassViewModel
import com.programmergabut.solatkuy.ui.main.home.HomeFragment
import com.programmergabut.solatkuy.ui.main.home.HomeViewModel
import com.programmergabut.solatkuy.quran.quran.listsurah.ListSurahFragment
import com.programmergabut.solatkuy.quran.quran.listsurah.ListSurahViewModel
import com.programmergabut.solatkuy.ui.main.setting.SettingViewModel
import com.programmergabut.solatkuy.ui.main.setting.SettingFragment
import com.programmergabut.solatkuy.quran.quran.readsurah.ReadSurahFragment
import com.programmergabut.solatkuy.quran.quran.readsurah.ReadSurahViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SolatKuyFragmentFactoryAndroidTest @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            com.programmergabut.solatkuy.quran.quran.readsurah.ReadSurahFragment::class.java.name -> com.programmergabut.solatkuy.quran.quran.readsurah.ReadSurahFragment(
                com.programmergabut.solatkuy.quran.quran.readsurah.ReadSurahViewModel(
                    FakeQuranRepositoryAndroidTest()
                )
            )
            CompassFragment::class.java.name -> CompassFragment(
                CompassViewModel(FakePrayerRepositoryAndroidTest())
            )
            com.programmergabut.solatkuy.quran.quran.listsurah.ListSurahFragment::class.java.name -> com.programmergabut.solatkuy.quran.quran.listsurah.ListSurahFragment(
                com.programmergabut.solatkuy.quran.quran.listsurah.ListSurahViewModel(
                    FakeQuranRepositoryAndroidTest()
                )
            )
            SettingFragment::class.java.name -> SettingFragment(
                SettingViewModel(FakePrayerRepositoryAndroidTest())
            )
            HomeFragment::class.java.name -> HomeFragment(
                HomeViewModel(FakePrayerRepositoryAndroidTest(), FakeQuranRepositoryAndroidTest())
            )
            else -> super.instantiate(classLoader, className)
        }

    }
}