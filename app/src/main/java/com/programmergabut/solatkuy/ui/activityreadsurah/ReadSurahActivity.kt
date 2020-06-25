package com.programmergabut.solatkuy.ui.activityreadsurah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmergabut.solatkuy.R
import com.programmergabut.solatkuy.data.local.localentity.MsFavSurah
import com.programmergabut.solatkuy.data.remote.remoteentity.readsurahJsonAr.Data
import com.programmergabut.solatkuy.util.enumclass.EnumStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_read_surah.*

@AndroidEntryPoint
class ReadSurahActivity : AppCompatActivity() {

    companion object{
        const val surahID = "surahID"
        const val surahName = "surahName"
        const val surahTranslation = "surahTranslation"
    }

    //private lateinit var readSurahViewModel: ReadSurahViewModel
    private val readSurahViewModel: ReadSurahViewModel by viewModels()

    private lateinit var readSurahAdapter: ReadSurahAdapter

    private lateinit var mSelSurahId: String
    private lateinit var mSelSurahName: String
    private lateinit var mSelSurahTranslation: String

    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_surah)

        /* readSurahViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(
            this.application))[ReadSurahViewModel::class.java] */


        getIntentExtra()

        readSurahViewModel.getFavSurah(mSelSurahId.toInt())

        setupToolbar()
        initRVReadSurah()
        observeApi()
    }

    private fun getIntentExtra() {
        mSelSurahId = intent.getStringExtra(surahID) ?: throw Exception("getExtras surahID")
        mSelSurahName = intent.getStringExtra(surahName) ?: throw Exception("getExtras surahName")
        mSelSurahTranslation = intent.getStringExtra(surahTranslation) ?: throw Exception("getExtras surahTranslation")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        this.mMenu = menu
        menuInflater.inflate(R.menu.read_surah_menu, menu)

        readSurahViewModel.msFavSurah.observe(this, Observer {
            if(it == null)
                menu?.findItem(R.id.i_star_surah)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_24)
            else
                menu?.findItem(R.id.i_star_surah)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_yellow_24)
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.i_star_surah -> {
                if(mMenu?.findItem(R.id.i_star_surah)?.icon?.constantState == this.getDrawable(R.drawable.ic_star_24)?.constantState)
                    readSurahViewModel.insertFavSurah(MsFavSurah(mSelSurahId.toInt(), mSelSurahName, mSelSurahTranslation))
                else
                    readSurahViewModel.deleteFavSurah(MsFavSurah(mSelSurahId.toInt(), mSelSurahName, mSelSurahTranslation))
                true
            }
            else -> super.onOptionsItemSelected(item!!)
        }
    }

    private fun observeApi(){

        var data: Data? = null

        readSurahViewModel.selectedSurahAr.observe(this, Observer {

            when(it.status){
                EnumStatus.SUCCESS -> {

                    if(it.data == null)
                        throw Exception(Thread.currentThread().stackTrace[1].methodName)

                    data = it.data.data

                    rv_read_surah.visibility = View.VISIBLE

                    ab_readQuran.visibility = View.VISIBLE
                    tb_readQuran.title = data!!.englishName
                    tb_readQuran.subtitle = data!!.revelationType + " - " + data!!.numberOfAyahs + " Ayahs"

                    cc_readQuran_loading.visibility = View.GONE

                    readSurahViewModel.fetchFavoriteData(mSelSurahId.toInt())
                }
                EnumStatus.LOADING -> {
                    ab_readQuran.visibility = View.INVISIBLE
                    cc_readQuran_loading.visibility = View.VISIBLE
                    tb_readQuran.title = ""

                    rv_read_surah.visibility = View.INVISIBLE
                }
                EnumStatus.ERROR -> {
                    ab_readQuran.visibility = View.INVISIBLE
                    lottieAnimationView.cancelAnimation()
                    tv_readQuran_loading.text = getString(R.string.fetch_failed)

                    rv_read_surah.visibility = View.INVISIBLE
                }
            }
        })

        readSurahViewModel.msFavAyahBySurahID.observe(this, Observer { local ->

            when(local.status){
                EnumStatus.SUCCESS -> {

                    local.data?.forEach { ayah ->
                        data?.ayahs?.forEach out@{remoteAyah ->
                            if(remoteAyah.numberInSurah == ayah.ayahID && mSelSurahId.toInt() == ayah.surahID){
                                remoteAyah.isFav = true
                                return@out
                            }
                        }
                    }

                    readSurahAdapter.setAyah(data?.ayahs!!)
                    readSurahAdapter.notifyDataSetChanged()

                }
                EnumStatus.LOADING -> {}
                EnumStatus.ERROR -> {}
            }

        })


        readSurahViewModel.fetchQuranSurah(mSelSurahId.toInt())
    }

    private fun setupToolbar() {
        setSupportActionBar(tb_readQuran)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun initRVReadSurah() {
        readSurahAdapter = ReadSurahAdapter(this, readSurahViewModel, mSelSurahId, mSelSurahName)

        rv_read_surah.apply {
            adapter = readSurahAdapter
            layoutManager = LinearLayoutManager(this@ReadSurahActivity)
            setHasFixedSize(true)
        }
    }

}