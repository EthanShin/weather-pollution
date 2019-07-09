package com.example.weatherpollution.Data

import java.io.Serializable

class DustData(
    var list: ArrayList<DustList>? = null
): Serializable

class DustList(
    var dataTime: String? = null,
    var pm10Value: String? = null,
    var pm25Value: String? = null
): Serializable