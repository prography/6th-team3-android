package com.prography.pethotel.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlin.math.acos


enum class TokenState{
    REMOVED, STORED
}

class AuthTokenViewModel (application: Application) :AndroidViewModel(application){

    /*로그인, 로그아웃
    * 현재는 shared preference 로 관리하고, 나중에 Database Table 로도 옮길 예정 */
    val userTokenState : MutableLiveData<TokenState> = MutableLiveData()

    fun isTokenValid(activity: Activity) : Boolean{
        val pref = activity.getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        val token = pref.getString(USER_TOKEN, "")
        return !token.isNullOrEmpty()
    }

    fun removeUserToken(activity: Activity){
        val pref = activity.getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        pref.edit().clear().apply()
        userTokenState.value =
            TokenState.REMOVED
        Log.d(TAG, "removeUserToken: User Token Removed!")
    }

    fun setUserToken(activity: Activity, userToken: String){
        val pref = activity.getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        pref.edit().putString(USER_TOKEN, userToken).apply()
        userTokenState.value = TokenState.STORED
    }

    fun getUserToken(activity: Activity) : String{
        var token : String = ""
        if(isTokenValid(activity)){
            val pref = activity.getSharedPreferences(USER_TOKEN, MODE_PRIVATE)
            token = pref.getString(USER_TOKEN, "")!!
        }
        return token
    }

    fun setUserId(activity: Activity, userId : Int){
        Log.d(TAG, "setUserId: 유저 아이디 셋")
        val pref = activity.getSharedPreferences("USER_ID", MODE_PRIVATE)
        pref.edit().putInt("USER_ID", userId).apply()
    }

    fun getUserId(activity: Activity): Int {
        Log.d(TAG, "getUserId: 유저 아이디 GET")
        val pref = activity.getSharedPreferences("USER_ID", MODE_PRIVATE)
        return pref.getInt("USER_ID", -1)
    }

    fun updateTokenState(tokenState: TokenState){
        userTokenState.value = tokenState
    }

    companion object {
        private const val TAG = "AuthTokenViewModel"
    }

}