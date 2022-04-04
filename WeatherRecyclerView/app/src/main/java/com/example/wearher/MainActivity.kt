package com.example.wearher

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.example.wearher.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var connectivityManager: ConnectivityManager
    lateinit var dataWeather: DataWeather
    private val dataModel: DataModel by viewModels()
    private var cityList: ArrayList<String> = arrayListOf("Irkutsk", "Angarsk", "Dubai")
//    private var cityList: ArrayList<String> = resources.getStringArray(R.array.name_cities)
    private val adapter = MyAdapter(cityList)
    private var editLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        init()
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                adapter.addName(it.data?.getStringExtra("res").toString())
            }
        }

        dataModel.indexCity.observe(this) {
            GlobalScope.launch (Dispatchers.IO) {
                    loadWeather(cityList[it])
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun updateLocale(c: Context, localeToSwitchTo: Locale): ContextWrapper {
        var context = c
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(localeToSwitchTo)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
        } else {
            configuration.locale = localeToSwitchTo
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context = context.createConfigurationContext(configuration)
        } else {
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return ContextUtils(context)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.eng -> {
                updateLocale(this, Locale.ENGLISH)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            R.id.rus -> {
                updateLocale(this, Locale.FRENCH)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return true
    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rcView.adapter = adapter
            rcView.attachSnapHelperWithListener(PagerSnapHelper())
            addBtn.setOnClickListener {
                editLauncher?.launch(Intent(this@MainActivity, EditActivity::class.java))
            }
        }
        val fr = supportFragmentManager.findFragmentById(R.id.fragment_weather)
        if (fr == null) {
            displayFragment(FullWeatherFragment.newInstance())
        }
    }

    fun RecyclerView.attachSnapHelperWithListener(
        snapHelper: SnapHelper,
        onSnapPositionChangeListener: OnSnapPositionChangeListener? = null) {
            snapHelper.attachToRecyclerView(this)
            val snapOnScrollListener = ScrollListener(snapHelper, onSnapPositionChangeListener, dataModel)
            addOnScrollListener(snapOnScrollListener)
    }

    suspend fun loadWeather(nameCity: String) {
        Log.d("WeatherConnection", connectivityManager.isActiveNetworkMetered.toString())
        if (connectivityManager.isActiveNetworkMetered) {
            val gson = Gson()

            val API_KEY = resources.getString(R.string.keyAPI)
            val weatherURL =
                "https://api.openweathermap.org/data/2.5/weather?q=$nameCity&appid=$API_KEY&units=metric";
            val stream = URL(weatherURL).getContent() as InputStream

            // JSON отдаётся одной строкой,
            val data = Scanner(stream).nextLine()
            Log.d("data", data)
            dataWeather = gson.fromJson(data, DataWeather::class.java)

            GlobalScope.launch (Dispatchers.Main) {
                dataModel.data.value = dataWeather
            }
        } else {
            var toast = Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_weather, fragment)
            .commit()
    }
}