package com.prography.pethotel.ui.places

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.api.main.MainApi
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.api.main.response.HotelImage
import kotlinx.coroutines.*


private const val TAG = "PlaceInfoViewModel"

class PlaceInfoViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope  = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _hotelList = MutableLiveData<ArrayList<HotelData>>()
    val hotelList : LiveData<ArrayList<HotelData>>
        get() = _hotelList

    private val _hotelImages = MutableLiveData<ArrayList<HotelImage>>()
    val hotelImages : LiveData<ArrayList<HotelImage>>
        get() = _hotelImages

    init {
        getHotelLists()

    }
    fun getHotelLists(){
        Log.d(TAG, "getHotelLists")
        try {
            uiScope.launch {
                val response = MainApi.petHotelRetrofitService.getHotelList()
                val hotels = response.data
                _hotelList.value = hotels as ArrayList<HotelData>
//                hotels.forEach {
//                    getHotelImages(it)
//                    delay(500)
//                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun setDistanceToHotelList(lat : Double, long : Double){
        Log.d(TAG, "setDistanceToHotelList: ")
        val result = FloatArray(3)

        _hotelList.value?.forEach {
            hotelData ->
            if(hotelData.longitude != null && hotelData.latitude != null){
                Location.distanceBetween(
                    lat, long,
                    hotelData.longitude, hotelData.latitude, result
                )
                Log.d(TAG, "setDistanceToHotelList: ${result[0]}")
                hotelData.distanceFromUser = (result[0] / 1000).toInt()
            }else{
                hotelData.distanceFromUser = Int.MAX_VALUE //거리 정보 없음
            }
            Log.d(TAG, "setDistanceToHotelList: ${hotelData.distanceFromUser}")
        }

    }

    private fun getHotelImages(hotelData: HotelData){
        Log.d(TAG, "getHotelImages")
        try {
            uiScope.launch {
                val response = MainApi.petHotelImageService.getHotelImage(query = hotelData.name)
                val images = response.items

                if(images.isNotEmpty()){
                    hotelData.hotelImageLinks = images
                }
                val index = _hotelList.value?.indexOf(hotelData)
                if (index != null) {
                    _hotelList.value?.set(index, hotelData)
                }
                Log.d(TAG, "getHotelImages: $images")
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    // 호텔 아이디로 호텔 가져오기
    private val _hotel: MutableLiveData<HotelData> = MutableLiveData()
    val hotel : LiveData<HotelData>
        get() = _hotel

    private suspend fun getHotelById(id : Int){
        try {
            uiScope.launch {
                val response = MainApi.petHotelRetrofitService.getHotelById(id)
                //TODO response 타입이랑 모델 일치시키기
                _hotel.value = response
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    //TODO 아래에 해당하는 쿼리 다 날릴지 아니면 안드쪽에서 필터 구현할지 ...

//       @GET("api/hotels")
//    suspend fun searchHotelByName(@Query("searchquery") hotelName : String) : List<Hotel>
//
//    @GET("api/hotels")
//    suspend fun searchHotelByMonitoring(@Query("monitoringquery") monitorName : String) : List<Hotel>
//
//    @GET("api/hotels")
//    suspend fun searchHotelByService(@Query("servicequery") serviceName : String) : List<Hotel>
//
//    @GET("api/hotels")
//    suspend fun searchHotelByTime(@Query("opentimequery") openTime : String, @Query("closetimequery") closeTime : String) : List<Hotel>


    private val _hotelSearchResult: MutableLiveData<List<HotelData>> = MutableLiveData()
    val hotelSearchResult : LiveData<List<HotelData>>
        get() = _hotelSearchResult
    private suspend fun searchHotelByName(name : String){
        try {
            uiScope.launch {
                val response = MainApi.petHotelRetrofitService.searchHotelByName(name)
                _hotelSearchResult.value = response
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    override fun onCleared() {
        viewModelJob.cancel()
    }
}