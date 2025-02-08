package com.example.sunnyweather2.logic.model

import com.google.gson.annotations.SerializedName


//在Call的泛型中传入数据类，这样retrofit会自动将响应体转换成数据类
data class PlaceResponse(val status: String, val places: List<Place>)
data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)
data class Location(val lng: String, val lat: String)