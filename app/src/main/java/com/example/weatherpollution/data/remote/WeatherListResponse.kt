package com.example.weatherpollution.data.remote

import java.io.Serializable

data class WeatherListResponse(
    var id: Int? = null,
    var description: String? = null,
    var icon: String? = null,
    var main: String? = null
): Serializable