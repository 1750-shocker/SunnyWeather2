package com.example.sunnyweather2.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//一个单例类，封装通用的Retrofit方法
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    //构建retrofit是通用的，在单例类中构建以直接调用
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //在retrofit对象的create对象中传入接口的class对象，返回接口的实现类
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    //泛型实化
    inline fun <reified T> create(): T = create(T::class.java)
}