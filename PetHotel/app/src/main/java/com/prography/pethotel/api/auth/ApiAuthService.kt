package com.prography.pethotel.api.auth

import com.prography.pethotel.api.auth.request.KakaoRegisterBody
import com.prography.pethotel.api.auth.request.LoginInfoBody
import com.prography.pethotel.api.auth.request.RegisterPetBody
import com.prography.pethotel.api.auth.request.RegisterUserInfo
import com.prography.pethotel.api.auth.response.GeneralLoginResponse
import com.prography.pethotel.api.auth.response.PetNumResponse
import com.prography.pethotel.api.auth.response.PostPetResponse
import com.prography.pethotel.api.auth.response.RegistrationResponse
import com.prography.pethotel.api.main.response.UserInfoResponse
import com.prography.pethotel.utils.ANIMAL_NUM_BASE_URL
import com.prography.pethotel.utils.BASE_URL
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


//GET https://api.mypetmily.net/hotels

private val authApiInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
private val okClient = OkHttpClient.Builder().addInterceptor(authApiInterceptor).build()

private val authRetrofit
        = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okClient)
    .baseUrl(BASE_URL)
    .build()

private val publicApiRetrofit
        = Retrofit.Builder()
    .addConverterFactory(TikXmlConverterFactory.create())
    .baseUrl(ANIMAL_NUM_BASE_URL)
    .build()

interface AnimalNumberApiService{

    @GET("service/rest/animalInfoSrvc/animalInfo")
    fun getAnimalCheckResponse(
        @Query("ServiceKey") serviceKey : String,
        @Query("dogregno") dog_reg_no : String,
        @Query("rfid_cd") rfid_cd : String
    ) : Call<PetNumResponse>

}

interface  AuthApiService{

    @POST("auth/login")
    suspend fun generalLogin(@Body loginInfo : LoginInfoBody) : GeneralLoginResponse

    @POST("user")
    fun generalRegister(@Body registerUserInfo : RegisterUserInfo) : Call<RegistrationResponse>

    @GET("user")
    fun getUser(@Header("Authorization") token : String) : Call<UserInfoResponse> //GET 마이페이지

    @POST("pet")
    fun registerPet(@Header("Authorization") token : String,
                            @Body registerPetBody: RegisterPetBody) : Call<PostPetResponse>

    @POST("user")
    suspend fun kakaoRegister(@Body kakaoRegisterBody: KakaoRegisterBody) : RegistrationResponse

}



object PetmilyAuthApi{
    val publicApiRetrofitService : AnimalNumberApiService by lazy {
        publicApiRetrofit.create(AnimalNumberApiService::class.java)
    }

    val authApiRetrofitService : AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }

}

