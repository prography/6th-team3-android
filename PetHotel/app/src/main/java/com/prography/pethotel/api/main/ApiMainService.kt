package com.prography.pethotel.api.main

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prography.pethotel.api.main.response.AnimalCheckResponse
import com.prography.pethotel.models.Hotel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//호텔 정보 받아오는 API

//Retrofit 객체 생성하기

// TODO 서버에서 base url 받기
private const val BASE_URL = "http://mypetmily-release.ap-northeast-2.elasticbeanstalk.com/"

private val retrofit
        = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface HotelsApiService{

    @GET("api/hotels")
    suspend fun getHotelList() : List<Hotel>

    @GET("api/hotels/{hotelId}")
    suspend fun getHotelById(@Path("hotelId") hotelId : Int) : Hotel
}

interface AnimalNumberApiService{

// TODO 서버에서 엔드포인트 받기.
    @GET("")
    suspend fun getAnimalCheckResponse(@Query("dog_reg_no") dog_reg_no : String,
                                       @Query("rfid_cd") rfid_cd : String) : AnimalCheckResponse

}

object HotelsApi{
    val retrofitService : HotelsApiService by lazy {
        retrofit.create(HotelsApiService::class.java)
    }
}

object AnimalCheckApi{
    val retrofitService : AnimalNumberApiService by lazy {
        retrofit.create(AnimalNumberApiService::class.java)
    }
}