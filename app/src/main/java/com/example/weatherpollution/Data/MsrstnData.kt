package com.example.weatherpollution.Data

import java.io.Serializable

class MsrstnData(
    var list: ArrayList<List>? = null
): Serializable

class List(
    var stationName: String? = null
): Serializable