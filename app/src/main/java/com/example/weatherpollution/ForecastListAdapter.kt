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
        val tempView = itemView.textView3
        val humidityView = itemView.textView4
        val mainView = itemView.textView5
        val dateView = itemView.textView6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListAdapter.ForecastViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun getItemCount() = forecast.size

    override fun onBindViewHolder(holder: ForecastListAdapter.ForecastViewHolder, position: Int) {
        val current = forecast[position]
        holder.tempView.text = current.temp.toString()
        holder.humidityView.text = current.humidity.toString()
        holder.mainView.text = current.main
        holder.dateView.text = current.dateTime
    }

    internal fun setForecasts(forecast: List<Forecast>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }
}