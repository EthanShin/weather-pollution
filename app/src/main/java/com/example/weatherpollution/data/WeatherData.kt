package com.example.weatherpollution.data

import java.io.Serializable

data class Weather(
    var response: Response? = null
): Serializable

class Response(
    var header: Header? = null,
    var body: Body? = null
): Serializable

class Header(
    var resultCode: String? = null,
    var resultMsg: String? = null
): Serializable

class Body(
    var items: Items? = null
): Serializable

class Items(
    var item: ArrayList<Item>? = null
): Serializable

class Item(
    var baseDate: String? = null,
    var baseTime: String? = null,
    var category: String? = null,
    var obsrValue: String? = null,
    var nx: String? = null,
    var ny: String? = null
): Serializable