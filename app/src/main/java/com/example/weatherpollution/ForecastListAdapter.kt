package com.example.weatherpollution

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherpollution.data.db.Forecast
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
        val current = forecast[position]
        holder.timeView.text = current.dateTime
        holder.imageView.setImageResource(R.drawable.ic_cloud)
        holder.tempView.text = current.temp.toString()
        holder.humidityView.text = current.humidity.toString()
    }

    internal fun setForecasts(forecast: List<Forecast>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }
}