package com.prography.pethotel.api.auth

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prography.pethotel.models.LoginInfo
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.models.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "http://mypetmily-release.ap-northeast-2.elasticbeanstalk.com/"

private val authRetrofit
        = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface  AuthApiService{


    @GET("api/auth/kakao/login")
    suspend fun kakaoLogin()


    @POST("api/user")
    suspend fun kakaoRegister(userInfo : UserInfo)

//    {
//        "userId": 2,
//        "data": {
//        "nickname": "털실맘",
//        "phoneNumber": "010-1111-2222",
//        "profileImage": "http://k.kakaocdn.net/dn/dNuqx8/btqEmMbuZcS/udnYZqfZRljOVTNxlBunp1/img_640x640.jpg"
//    }
//    }

    @Headers("Authorization : === token === ")
    @GET("api/auth/login")
    suspend fun generalLogin(loginInfo : LoginInfo)
    //{
    //	"email": "test@gmail.com",
    //	"password": "testtest00"
    //}


    // TODO return 값이 뭔지 ?
    @Headers("Authorization : ====token====")
    @GET("api/user")
    suspend fun getUser()

    @POST("api/user")
    suspend fun generalRegister(userInfo : UserInfo)
//    {
//	"data": {
//		"nickname": "test99",
//		"phoneNumber": "010-3333-4444",
//		"email": "test@gmail.com",
//		"password": "testtest00"
//	}
//}

    @Headers("Authorization : === token === ")
    @POST("api/pet")
    suspend fun registerPet(petInfo : PetInfo)
//{
//	"userId": 3,
//	"data": [
//		{
//			"petName": "털실이",
//			"registerNumber": "410000000227825",
//			"birthYear": 2019
//		}
//	]
//}

}

