package com.example.weatherpollution

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherpollution.data.db.Forecast
import kotlinx.android.synthetic.main.fragment_show.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ForecastListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var forecast = emptyList<Forecast>()

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeView = itemView.forecast_time
        val imageView = itemView.forecast_icon
        val tempView = itemView.forecast_temp
        val humidityView = itemView.forecast_humidity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun getItemCount() = forecast.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecast[position]
        holder.timeView.text = forecast.dateTime
        holder.imageView.setImageResource(selectImage(forecast.icon))
        holder.tempView.text = forecast.temp.toString()
        holder.humidityView.text = forecast.humidity.toString()
    }

    private fun selectImage(icon: String?): Int {
        return when(icon) {
            "01d" -> R.drawable.ic_sun
            "01n" -> R.drawable.ic_moon
            "02d", "02n", "03d", "03n", "04d", "04n" -> R.drawable.ic_cloud
            "09d", "09n", "10d", "10n" -> R.drawable.ic_rain
            "11d", "11n" -> R.drawable.ic_bolt
            "13d", "13n" -> R.drawable.ic_snow
            "50d", "50n" -> R.drawable.ic_mist
            else -> 0
        }
    }

    internal fun setForecasts(forecast: List<Forecast>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }
}