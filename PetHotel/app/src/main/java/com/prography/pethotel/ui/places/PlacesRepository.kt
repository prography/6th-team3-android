package com.prography.pethotel.ui.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prography.pethotel.api.main.MainApi
import com.prography.pethotel.models.Hotel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class PlacesRepository(
    val mainApi : MainApi
){

    //TODO retrofit 결과에 따라 처리해주기

    private val viewModelJob = Job()
    private val uiScope  = CoroutineScope(Dispatchers.Main + viewModelJob)


    //TODO 호텔 정보 가져오기 - 모델 클래스 테이블에 맞게 수정하기
    private val _hotelList : MutableLiveData<List<Hotel>> = MutableLiveData()
    val hotelList : LiveData<List<Hotel>>
        get() = _hotelList

    private suspend fun getHotelLists(){
        try {
            uiScope.launch {
                val response = mainApi.petHotelRetrofitService.getHotelList()
                _hotelList.value = response
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }



    // 호텔 아이디로 호텔 가져오기
    private val _hotel: MutableLiveData<Hotel> = MutableLiveData()
    val hotel : LiveData<Hotel>
        get() = _hotel

    private suspend fun getHotelById(id : Int){
        try {
            uiScope.launch {
                val response = mainApi.petHotelRetrofitService.getHotelById(id)
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


    private val _hotelSearchResult: MutableLiveData<List<Hotel>> = MutableLiveData()
    val hotelSearchResult : LiveData<List<Hotel>>
        get() = _hotelSearchResult
    private suspend fun searchHotelByName(name : String){
        try {
            uiScope.launch {
                val response = mainApi.petHotelRetrofitService.searchHotelByName(name)
                _hotelSearchResult.value = response
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    fun cancelJob(){
        viewModelJob.cancel()
    }
}