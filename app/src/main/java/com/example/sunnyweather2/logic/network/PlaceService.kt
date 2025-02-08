package com.example.sunnyweather2.logic.network

import com.example.sunnyweather2.SunnyWeatherApplication
import com.example.sunnyweather2.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 网络请求的接口对应一类网络请求，比如天气数据请求，城市数据请求，这里的接口对应城市数据请求
// Retrofit建议使用功能种类名开头，service结尾的接口名，里面包含具体的请求方法，映射的是HTTP请求，其返回值必须是Call
//泛型指明将相应的数据转换成的类型，也就是数据类对应json
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    //注解指明请求的url，query是请求的参数
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}