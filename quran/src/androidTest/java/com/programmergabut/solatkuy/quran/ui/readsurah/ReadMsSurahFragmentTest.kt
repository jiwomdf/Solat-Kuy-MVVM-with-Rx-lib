package com.programmergabut.solatkuy.quran.ui.readsurah

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.programmergabut.solatkuy.quran.DummyValueAndroidTest
import com.programmergabut.solatkuy.quran.R
import com.programmergabut.solatkuy.quran.launchFragmentInHiltContainer
import com.programmergabut.solatkuy.quran.ui.SolatKuyFragmentFactoryAndroidTest
import com.programmergabut.quran.util.idlingresource.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ReadMsSurahFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    lateinit var fragmentFactory: SolatKuyFragmentFactoryAndroidTest

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun testVisibilityAndData_componentDisplayedWithCorrectValue(){
        var testViewModel: ReadSurahViewModel? = null
        val initData = DummyValueAndroidTest.fetchAllSurah<ReadMsSurahFragmentTest>().data.last()
        val arg = Bundle()
        arg.putString("selectedSurahId", initData.number.toString())
        arg.putString("selectedSurahName", initData.englishName)
        arg.putString("selectedTranslation", initData.englishNameTranslation)
        arg.putBoolean("isAutoScroll", false)
        launchFragmentInHiltContainer<ReadSurahFragment>(
            fragmentArgs = arg,
            fragmentFactory = fragmentFactory
        ) {
            testViewModel = viewModel
        }

        onView(withId(R.id.ab_readQuran)).check(matches(isDisplayed()))
        onView(withId(R.id.tb_readSurah)).check(matches(isDisplayed()))
        onView(withId(R.id.i_star_surah)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_read_surah)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_more)).check(matches(isDisplayed()))
    }

    @Test
    fun testScrollToTheLastAyah_successfullyScrollToLastAyah(){
        var testViewModel: ReadSurahViewModel? = null
        val initData = DummyValueAndroidTest.fetchAllSurah<ReadMsSurahFragmentTest>().data.last()
        val arg = Bundle()
        arg.putString("selectedSurahId", initData.number.toString())
        arg.putString("selectedSurahName", initData.englishName)
        arg.putString("selectedTranslation", initData.englishNameTranslation)
        arg.putBoolean("isAutoScroll", false)
        launchFragmentInHiltContainer<ReadSurahFragment>(
            fragmentArgs = arg,
            fragmentFactory = fragmentFactory
        ){
            testViewModel = viewModel
        }

        onView(withId(R.id.rv_read_surah)).check(matches(isDisplayed()))

        val selectedSurahAr = testViewModel!!.selectedSurah.value
        val totalAyah = selectedSurahAr?.data!!.size - 1
        onView(withId(R.id.rv_read_surah)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(totalAyah, ViewActions.scrollTo())
        )
    }

    @Test
    fun testOpenFirstSurahThenClickFavorite_favSurahHasSaved(){
        var testViewModel: ReadSurahViewModel? = null
        val initData = DummyValueAndroidTest.fetchAllSurah<ReadMsSurahFragmentTest>().data.last()
        val arg = Bundle()
        arg.putString("selectedSurahId", initData.number.toString())
        arg.putString("selectedSurahName", initData.englishName)
        arg.putString("selectedTranslation", initData.englishNameTranslation)
        arg.putBoolean("isAutoScroll", false)
        launchFragmentInHiltContainer<ReadSurahFragment>(
            fragmentArgs = arg,
            fragmentFactory = fragmentFactory
        ){
            testViewModel = viewModel
        }

        onView(withId(R.id.i_star_surah)).check(matches(isDisplayed()))
        onView(withId(R.id.i_star_surah)).perform(click())
        assertEquals(testViewModel?.favSurahBySurahID?.value?.surahID,  initData.number)
        assertEquals(testViewModel?.favSurahBySurahID?.value?.surahName,  initData.englishName)
    }

    @Test
    fun testOpenLastSurahThanSwipeLeftFirstAyah(){
        var testViewModel: ReadSurahViewModel? = null
        val initData = DummyValueAndroidTest.fetchAllSurah<ReadMsSurahFragmentTest>().data.last()
        val arg = Bundle()
        arg.putString("selectedSurahId", initData.number.toString())
        arg.putString("selectedSurahName", initData.englishName)
        arg.putString("selectedTranslation", initData.englishNameTranslation)
        arg.putBoolean("isAutoScroll", false)
        launchFragmentInHiltContainer<ReadSurahFragment>(
            fragmentArgs = arg,
            fragmentFactory = fragmentFactory
        ) {
            testViewModel = viewModel
        }

        onView(withId(R.id.rv_read_surah)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_read_surah)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeLeft())
        )
    }

}