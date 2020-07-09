@file:Suppress("DEPRECATION")

package com.prography.pethotel.api.main

import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.api.main.response.HotelImage
import com.prography.pethotel.api.main.response.HotelImageResponse
import com.prography.pethotel.api.main.response.HotelListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//호텔 정보 받아오는 API
private const val BASE_URL = "https://api.mypetmily.net/"
private const val NAVER_BASE_URL = "https://openapi.naver.com/v1/"

private val petHotelRetrofit
        = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


private val petHotelImageSearchRetrofit
= Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(NAVER_BASE_URL)
    .build()


interface HotelsApiService{

    @GET("hotels")
    suspend fun getHotelList() : HotelListResponse

    @GET("hotels/{hotelId}")
    suspend fun getHotelById(@Path("hotelId") hotelId : Int) : HotelData

    @GET("hotels")
    suspend fun searchHotelByName(@Query("searchquery") hotelName : String) : List<HotelData>

    @GET("hotels")
    suspend fun searchHotelByMonitoring(@Query("monitoringquery") monitorName : String) : List<HotelData>

    @GET("hotels")
    suspend fun searchHotelByService(@Query("servicequery") serviceName : String) : List<HotelData>

    @GET("hotels")
    suspend fun searchHotelByTime(@Query("opentimequery") openTime : String, @Query("closetimequery") closeTime : String) : List<HotelData>

}

interface HotelsImageService{

    @Headers("X-Naver-Client-Id: ubtH0XiUQUZeoZfZllfX", "X-Naver-Client-Secret: Ag2zHxP5gc")
    @GET("search/image")
    suspend fun getHotelImage(@Query("query") query: String,
                              @Query("display") display: Int = 10,
                              @Query("start") start : Int = 1,
                              @Query("sort") sort : String = "sim",
                              @Query("filter") filter : String = "all"):HotelImageResponse
}



object MainApi{
    val petHotelRetrofitService : HotelsApiService by lazy {
        petHotelRetrofit.create(HotelsApiService::class.java)
    }

    val petHotelImageService : HotelsImageService by lazy {
        petHotelImageSearchRetrofit.create(HotelsImageService::class.java)
    }
}
