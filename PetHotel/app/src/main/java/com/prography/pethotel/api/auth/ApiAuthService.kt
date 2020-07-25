package com.prography.pethotel.api.auth

import com.prography.pethotel.api.auth.request.*
import com.prography.pethotel.api.auth.response.*
import com.prography.pethotel.api.main.response.UserInfoResponse
import com.prography.pethotel.utils.ANIMAL_NUM_BASE_URL
import com.prography.pethotel.utils.BASE_URL
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.Header


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
    fun generalLogin(@Body loginInfo : LoginInfoBody) : Call<GeneralLoginResponse>

    @POST("user")
    fun generalRegister(@Body registerUserInfo : RegisterUserInfo) : Call<RegistrationResponse>

    @Multipart
    @POST("user")
    fun generalRegisterForm(
        @Part("data[nickname]") nickName : RequestBody,
        @Part("data[phoneNumber]") phoneNumber : RequestBody,
        @Part("data[email]") email : RequestBody,
        @Part("data[password]") password : RequestBody,
        @Part photoUrl: MultipartBody.Part?
     ): Call<RegistrationResponse>

    @GET("user")
    fun getUser(@Header("Authorization") token : String) : Call<UserInfoResponse> //GET 마이페이지

    @POST("pet/check")
    fun checkPet(@Body checkPetBody: CheckPetBody) : Call<CheckPetResponse>

//    @POST("pet")
//    fun registerPet(@Header("Authorization") token : String,
//                            @Body registerPetBody: RegisterPetBody) : Call<PostPetResponse>

    @Multipart
    @POST("pet")
    fun registerPetForm(@Header("Authorization") token: String,
                        @Part("data[petName]") petName: RequestBody,
                        @Part("data[registerNumber]") registerNumber: RequestBody,
                        @Part("data[year]") birthYear: RequestBody,
                        @Part petProfileMultipart: MultipartBody.Part?,
                        @Part("data[breed]") breed: RequestBody,
                        @Part("data[isNeutered]") isNeutered: RequestBody,
                        @Part("data[gender]") gender: RequestBody,
                        @Part("data[rfidCode]") rfidCode: RequestBody
            ) : Call<PostPetResponse>

    @POST("user")
    suspend fun kakaoRegister(@Body kakaoRegisterBody: KakaoRegisterBody) : RegistrationResponse

    @Multipart
    @POST("user")
    fun kakaoRegisterForm(
        @Part("userId") userId : RequestBody?,
        @Part("data[nickname]") nickname: RequestBody?,
        @Part("data[phoneNumber]") phoneNumber: RequestBody?,
        @Part("data[profileImage]") profileImageUrl: RequestBody?
    ) : Call<RegistrationResponse>

}



object PetmilyAuthApi{
    val publicApiRetrofitService : AnimalNumberApiService by lazy {
        publicApiRetrofit.create(AnimalNumberApiService::class.java)
    }

    val authApiRetrofitService : AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }

}

