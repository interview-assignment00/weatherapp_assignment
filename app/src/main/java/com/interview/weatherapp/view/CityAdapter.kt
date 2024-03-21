package com.interview.weatherapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.interview.weatherapp.BuildConfig
import com.interview.weatherapp.databinding.ItemWeatherBinding
import com.interview.weatherapp.model.CityInfo
import com.interview.weatherapp.model.Display
import com.interview.weatherapp.repository.LocationRepository
import com.interview.weatherapp.repository.CityInfoRepository
import com.interview.weatherapp.utils.ImageIdConverter
import com.interview.weatherapp.utils.KalvinConverter
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CityAdapter(
    private val displayUpdate: (display: Display) -> Unit,
    private val locationRepository: LocationRepository = LocationRepository(),
    private val cityRepository: CityInfoRepository = CityInfoRepository()
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private val disposables = CompositeDisposable()

    inner class ViewHolder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    // This is not a good solution, but I had to do it this way,
    // because I only have access to this one endpoint
    // that only retrieves weather by one location per call
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val location = locationRepository.coordinates[position]
        val disposable = cityRepository.cityInfo(
            coordinates = location, appKey = BuildConfig.API_KEY
        ).subscribe(
            { response ->
                viewHolder.binding.city.text = response.name
                viewHolder.binding.title.text = response.weather[0].main
                viewHolder.binding.description.text = response.weather[0].description
                val imageId = response.weather[0].icon // For simplicity, only first element is fetched
                viewHolder.binding.icon.setImageResource(ImageIdConverter.drawable(imageId))
                val kalvin = response.main.temp
                val celsius = KalvinConverter.toCelsiusString(kalvin)
                viewHolder.binding.temperature.text = celsius
                viewHolder.binding.card.setOnClickListener {
                    updateDisplay(response, celsius)
                }
            },
            { error ->
                Log.e(TAG, "Error while updating weather info. Message: ${error.message.toString()}")
            }
        )
        disposables.add(disposable)
    }

    private fun updateDisplay(response: CityInfo, celsius: String) {
        val display = Display(
            city = response.name,
            description = response.weather[0].description,
            temperature = celsius
        )
        displayUpdate.invoke(display)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposables.clear()
    }

    override fun getItemCount(): Int = locationRepository.coordinates.size

    companion object {
        private const val TAG = "CityAdapter"
    }
}
