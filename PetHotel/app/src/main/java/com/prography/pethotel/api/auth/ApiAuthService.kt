package com.prography.pethotel.api.auth

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prography.pethotel.models.LoginInfo
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.models.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://api.mypetmily.net/"
//GET https://api.mypetmily.net/hotels
private val authRetrofit
        = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface  AuthApiService{


    //로그인 화면의 I세션 콜백으로 응답을 받는다.
    @GET("auth/kakao/login")
    suspend fun kakaoLogin()


    //유저의 정보를 넘겨준다.
    @POST("user")
    suspend fun kakaoRegister(@Body userInfo : UserInfo)
//유저 인포는 Gson 컨버터로 인해 자동으로 JSON으로 시리얼라이즈 된다.

//    {
//        "userId": 2,
//        "data": {
//        "nickname": "털실맘",
//        "phoneNumber": "010-1111-2222",
//        "profileImage": "http://k.kakaocdn.net/dn/dNuqx8/btqEmMbuZcS/udnYZqfZRljOVTNxlBunp1/img_640x640.jpg"
//    }
//    }

//    @Headers("Authorization : === token === ") TODO 헤더 필요 없는거 아닌가?
    @GET("auth/login")
    suspend fun generalLogin(@Body loginInfo : LoginInfo)
    //{
    //	"email": "test@gmail.com",
    //	"password": "testtest00"
    //}


    @Headers("Authorization : ====token====")
    @GET("user")
    suspend fun getUser() : UserInfo //GET 마이페이지

    @POST("user") //TODO 일반 회원가입이랑, 카카오랑 엔드포인트 같은데 요청 Body 가 다름
    suspend fun generalRegister(userInfo : UserInfo) : String
//    {
//	"data": {
//		"nickname": "test99",
//		"phoneNumber": "010-3333-4444",
//		"email": "test@gmail.com",
//		"password": "testtest00"
//	}
//}

    //TODO 토큰 값 뭔지 확인하기
    @Headers("Authorization : === token === ")
    @POST("api/pet")
    suspend fun registerPet(petInfo : PetInfo)
//{
//	"userId": 3, TODO 유저 아이디는 getUser() 로 받아오기
//	"data": [
//		{
//			"petName": "털실이",
//			"registerNumber": "410000000227825",
//			"birthYear": 2019
//		}
//	]
//}

}

