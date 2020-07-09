package com.prography.pethotel.api.auth

import com.prography.pethotel.api.auth.request.KakaoRegisterBody
import com.prography.pethotel.api.auth.request.LoginInfoBody
import com.prography.pethotel.api.auth.request.RegisterPetBody
import com.prography.pethotel.api.auth.request.RegisterUserInfo
import com.prography.pethotel.api.auth.response.GeneralLoginResponse
import com.prography.pethotel.api.auth.response.KakaoLoginResponse
import com.prography.pethotel.api.auth.response.PostPetResponse
import com.prography.pethotel.api.auth.response.RegistrationResponse
import com.prography.pethotel.api.main.response.PetNumberResponse
import com.prography.pethotel.api.main.response.UserInfoResponse
import com.prography.pethotel.utils.ANIMAL_NUM_BASE_URL
import com.prography.pethotel.utils.BASE_URL
import com.prography.pethotel.utils.SERVICE_KEY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.*



//GET https://api.mypetmily.net/hotels
private val authRetrofit
        = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

private val publicApiRetrofit
        = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .baseUrl(ANIMAL_NUM_BASE_URL)
    .build()

interface AnimalNumberApiService{

    @GET("service/rest/animalInfoSrvc/animalInfo")
    suspend fun getAnimalCheckResponse(@Query("dogregno") dog_reg_no : String,
                                       @Query("ServiceKey") serviceKey : String = SERVICE_KEY
    ) : PetNumberResponse

}

interface  AuthApiService{


    //로그인 화면의 I세션 콜백으로 응답을 받는다.
    @GET("auth/kakao/login")
    suspend fun kakaoLogin() : KakaoLoginResponse

    @POST("user")
    suspend fun kakaoRegister(@Body kakaoRegisterBody: KakaoRegisterBody) : RegistrationResponse

    @POST("auth/login")
    suspend fun generalLogin(@Body loginInfo : LoginInfoBody) : GeneralLoginResponse

    @POST("user")
    suspend fun generalRegister(@Body registerUserInfo : RegisterUserInfo) : RegistrationResponse

    @GET("user")
    suspend fun getUser(@Header("Authorization") token : String) : UserInfoResponse //GET 마이페이지

    @POST("pet")
    suspend fun registerPet(@Header("Authorization") token : String,
                            @Body registerPetBody: RegisterPetBody) : PostPetResponse

}


object PetmilyAuthApi{
    val publicApiRetrofitService : AnimalNumberApiService by lazy {
        publicApiRetrofit.create(AnimalNumberApiService::class.java)
    }

    val authApiRetrofitService : AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }
}

