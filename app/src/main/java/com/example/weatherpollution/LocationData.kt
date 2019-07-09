package com.example.weatherpollution

import java.io.Serializable

class LocationData(
    var meta: Meta? = null,
    var documents: ArrayList<Documents>? = null
): Serializable

class Meta(
    var total_count: String? = null
): Serializable

class Documents(
    var x: String? = null,
    var y: String? = null
): Serializable