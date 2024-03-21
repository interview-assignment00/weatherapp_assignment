package com.interview.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.interview.weatherapp.databinding.ActivityMainBinding
import com.interview.weatherapp.model.Display
import com.interview.weatherapp.repository.CityInfoRepository
import com.interview.weatherapp.repository.LocationRepository
import com.interview.weatherapp.utils.KalvinConverter
import com.interview.weatherapp.view.CityListFragment
import com.interview.weatherapp.viewmodel.DisplayViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val displayViewModel: DisplayViewModel by viewModels()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(displayViewModel.showDefaultCity) {
            val locations = LocationRepository().coordinates
            val cityInfoDisposable = CityInfoRepository().cityInfo(
                coordinates = locations[0], appKey = BuildConfig.API_KEY
            ).subscribe(
                { response ->
                    val celsius = KalvinConverter
                        .toCelsiusString(response.main.temp)
                    val display = Display(
                        city = response.name,
                        description = response.weather[0].description,
                        temperature = celsius
                    )
                    updateDisplay(display)
                    displayViewModel.updateDisplay(display)
                },
                { error ->
                    Toast.makeText(baseContext, "Unable to fetch weather info...${error.message}",
                        Toast.LENGTH_SHORT).show()
                }
            )
            disposables.add(cityInfoDisposable)
        }

        val displayDisposable = displayViewModel.display
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { display ->
                updateDisplay(display)
            }
        disposables.add(displayDisposable)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CityListFragment>(R.id.fragment_container)
        }
    }

    override fun onStop() {
        super.onStop()
        displayViewModel.shouldNotShowDefaultCity()
    }

    private fun updateDisplay(display: Display) {
        binding.weatherDisplay.city.text = display.city
        binding.weatherDisplay.description.text = display.description
        binding.weatherDisplay.temperature.text = display.temperature
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}