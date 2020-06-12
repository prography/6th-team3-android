@file:Suppress("DEPRECATION")

package com.prography.pethotel.api.main

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prography.pethotel.api.main.response.AnimalCheckResponse
import com.prography.pethotel.models.Hotel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//호텔 정보 받아오는 API

//Retrofit 객체 생성하기

private const val BASE_URL = "http://mypetmily-release.ap-northeast-2.elasticbeanstalk.com/"
private const val ANIMAL_NUM_BASE_URL = "http://openapi.animal.go.kr/openapi/"
private const val SERVICE_KEY = "D38lmfo5DkcPzGjgIdQOpytC0z0EIJ20ZmMhPJ7NOTl%2F%2F%2B3P19474bNkQFKemgHgW5sxVOoqveqeShaMnLuexg%3D%3D"



private val petHotelRetrofit
        = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

private val publicApiRetrofit
        = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(ANIMAL_NUM_BASE_URL)
    .build()

interface HotelsApiService{

    @GET("api/hotels")
    suspend fun getHotelList() : List<Hotel>

    @GET("api/hotels/{hotelId}")
    suspend fun getHotelById(@Path("hotelId") hotelId : Int) : Hotel

    @GET("api/hotels")
    suspend fun searchHotelByName(@Query("searchquery") hotelName : String) : List<Hotel>

    @GET("api/hotels")
    suspend fun searchHotelByMonitoring(@Query("monitoringquery") monitorName : String) : List<Hotel>

    @GET("api/hotels")
    suspend fun searchHotelByService(@Query("servicequery") serviceName : String) : List<Hotel>

    @GET("api/hotels")
    suspend fun searchHotelByTime(@Query("opentimequery") openTime : String, @Query("closetimequery") closeTime : String) : List<Hotel>

}



interface AnimalNumberApiService{

    @GET("service/rest/animalInfoSrvc/animalInfo")
    suspend fun getAnimalCheckResponse(@Query("dogregno") dog_reg_no : String,
                                       @Query("ServiceKey") serviceKey : String = SERVICE_KEY) : AnimalCheckResponse

}

object MainApi{
    val petHotelRetrofitService : HotelsApiService by lazy {
        petHotelRetrofit.create(HotelsApiService::class.java)
    }

    val publicApiRetrofitService : AnimalNumberApiService by lazy {
        publicApiRetrofit.create(AnimalNumberApiService::class.java)
    }
}
