package com.example.sunnyweather2.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather2.logic.model.Place
import com.example.sunnyweather2.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
//数据请求的中间人，会进行有网还是无网的判断去调用在线数据或者本地数据
object Repository {
    //这里用到了协程liveData，是一个协程的扩展函数，会自动在子线程中执行，然后将结果返回给主线程
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try{
            //这里就调用了刚才定义的挂起函数，这里直接获得响应体
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if(placeResponse.status =="ok"){
                val places = placeResponse.places
                //这里返回的是一个Result对象，Result是一个密封类，有success和failure两个子类，success表示成功，failure表示失败，
                // 这里的success和failure都是静态方法，会返回一个Result对象，这里的emit方法会将结果返回给主线程，他们封装了searchPlaces的结果
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        //在 Kotlin 协程中，emit 是 Flow 接口的一个方法，用于发送值到流中。Flow 是一种异步数据流，可以用于在协程中处理连续的事件或值。
        // 这一行代码的作用是将 result 对象发送到 LiveData 流中。
        // LiveData 是 Android 架构组件中的一个类，用于在应用组件（如 Activity 或 Fragment）之间传递数据
        emit(result)
    }
}