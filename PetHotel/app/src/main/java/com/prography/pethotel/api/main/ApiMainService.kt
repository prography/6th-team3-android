@file:Suppress("DEPRECATION")

package com.prography.pethotel.api.main

import com.prography.pethotel.api.main.request.*
import com.prography.pethotel.api.main.response.*
import retrofit2.Call
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

    /* 호텔 정보 관련 API */
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


    /* 호텔 리뷰 관련 API */
    @POST("reviews/{hotelId}")
    fun postHotelReview(@Path("hotelId") hotelId: Int,
                        @Body reviewPostBody: ReviewPostBody) : Call<PostReviewResponse>

    @GET("reviews/{hotelId}")
    fun getHotelReviews(@Path("hotelId") hotelId: Int) : Call<GetReviewResponse>

    @PUT("reviews/{hotelId}/{reviewId}")
    fun updateHotelReviewById(@Path("hotelId") hotelId: Int,
                              @Path("reviewId") reviewId : Int,
                              @Body reviewPostBody: ReviewPostBody) : Call<UpdateReviewResponse>

    @DELETE("reviews/{hotelId}/{reviewId}")
    fun deleteHotelReviewById(@Path("hotelId") hotelId: Int,
                              @Path("reviewId") reviewId: Int,
                              @Body reviewDeleteBody: ReviewDeleteBody) : Call<DeleteReviewResponse>

    /* 애견호텔 예약 관련 API */
    @POST("reservations/{hotelId}")
    fun postReservation(@Path("hotelId") hotelId: Int,
                        @Body reservationPostBody : ReservationPostBody) : Call<ReservationPostResponse>

    @GET("reservations/{hotelId}")
    fun getReservation(@Path("hotelId") hotelId: Int)

    @PUT("reservations/{hotelId}/{reservationId}")
    fun updateReservation(@Path("hotelId") hotelId: Int,
                          @Path("reservationId") reservationId : Int,
                          @Body updateReservationBody : UpdateReservationBody
    )

    @DELETE("reservations/{hotelId}/{reservationId}")
    fun deleteReservation(@Path("hotelId") hotelId: Int,
                          @Path("reservationId") reservationId: Int,
                          @Body deleteReservationBody : DeleteReservationBody
    )

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
