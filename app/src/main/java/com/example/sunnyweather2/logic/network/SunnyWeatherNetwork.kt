package com.example.sunnyweather2.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//这个类进行实际的网络请求，另外一个类进行本地数据请求
object SunnyWeatherNetwork {
    //创建retrofit对象，这是实际发起网络请求的主体（是传入的接口的动态代理对象，可以调用接口中的任何方法，Retrofit会自动处理）
    private val placeService = ServiceCreator.create<PlaceService>()

    //调用接口方法，这里用上协程和retrofit的结合写法，标准写法见书p454
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            //调用enqueue方法，retrofit会根据注解去发出http请求（请求自动在子线程运行）
            enqueue(object : Callback<T> {
                //请求结构会回调到Callback中，这是会自动切到主线程，
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()//直接得到解析后的数据
                    //得到响应体,resume方法会切回子线程
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}