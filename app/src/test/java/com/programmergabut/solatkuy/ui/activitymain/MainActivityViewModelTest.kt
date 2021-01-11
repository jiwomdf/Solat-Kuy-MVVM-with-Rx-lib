package com.programmergabut.solatkuy.ui.activitymain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.programmergabut.solatkuy.CoroutinesTestRule
import com.programmergabut.solatkuy.data.FakePrayerRepository
import com.programmergabut.solatkuy.ui.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    private lateinit var viewModel: MainActivityViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var prayerRepository: FakePrayerRepository


    @Before
    fun setUp() {
        viewModel = MainActivityViewModel(prayerRepository)
    }

    @Test
    fun getMsSetting() = coroutinesTestRule.testDispatcher.runBlockingTest {
        viewModel.msSetting
        Mockito.verify(prayerRepository).observeMsSetting()
    }
}