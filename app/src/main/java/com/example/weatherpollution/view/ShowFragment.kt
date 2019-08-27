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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ShowFragment : Fragment() {

    val viewModel: MainViewModel by sharedViewModel()
    var viewDataBinding: FragmentShowBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false)
        return viewDataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("TEST", "main weather db: ${it.temp}, ${it.humidity}, ${it.main}, ${it.dateTime}")

            viewDataBinding!!.weather = it
        })
    }
}
