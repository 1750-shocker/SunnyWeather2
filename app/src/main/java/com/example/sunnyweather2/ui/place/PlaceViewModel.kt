//这里用到ViewModel
package com.example.sunnyweather2.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import androidx.lifecycle.Transformations
import androidx.lifecycle.switchMap
import com.example.sunnyweather2.logic.Repository
import com.example.sunnyweather2.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()

    //    val placeLiveData:LiveData<List<Place>> = Transformations.switchMap(searchLiveData) { query ->
//        Repository.searchPlaces(query)
//    }
    val placeLiveData = searchLiveData.switchMap { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}
