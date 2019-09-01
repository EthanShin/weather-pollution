package com.example.weatherpollution.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.weatherpollution.R
import com.example.weatherpollution.databinding.FragmentShowBinding
import com.example.weatherpollution.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_show.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ShowFragment : Fragment() {

    val viewModel: MainViewModel by sharedViewModel()
    lateinit var viewDataBinding: FragmentShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("TEST", "main weather db: ${it?.temp}, ${it?.humidity}, ${it?.main}, ${it?.dateTime}")

            when(it?.icon) {
                "01d" -> current_icon.setImageResource(R.drawable.ic_sun)
                "01n" -> current_icon.setImageResource(R.drawable.ic_moon)
                "02d", "02n", "03d", "03n", "04d", "04n" -> current_icon.setImageResource(R.drawable.ic_cloud)
                "09d", "09n", "10d", "10n" -> current_icon.setImageResource(R.drawable.ic_rain)
                "11d", "11n" -> current_icon.setImageResource(R.drawable.ic_bolt)
                "13d", "13n" -> current_icon.setImageResource(R.drawable.ic_snow)
                "50d", "50n" -> current_icon.setImageResource(R.drawable.ic_mist)
                else -> current_icon.setImageResource(0)
            }

            viewDataBinding.weather = it
        })
    }
}
