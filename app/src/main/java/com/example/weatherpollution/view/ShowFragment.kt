package com.example.weatherpollution.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

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

//            val glide = Glide.with(this)
//            glide
//                .load(Uri.parse("http://openweathermap.org/img/w/" + it.icon + ".png"))
//                .override(current_icon.width, current_icon.height)
//                .into(current_icon)

            viewDataBinding.weather = it
        })
    }
}
