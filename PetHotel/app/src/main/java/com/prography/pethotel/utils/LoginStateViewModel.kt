package com.prography.pethotel.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

private const val TAG = "LoginStateViewModel"

enum class TokenState{
    REMOVED, STORED
}

class LoginStateViewModel (application: Application) :AndroidViewModel(application){

    val userTokenState : MutableLiveData<TokenState> = MutableLiveData()

    fun isTokenValid(activity: Activity) : Boolean{
        val pref = activity.getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        val token = pref.getString(USER_TOKEN, "")
        return !token.isNullOrEmpty()
    }

    fun removeUserToken(activity: Activity){
        val pref = activity.getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        pref.edit().clear().apply()
        userTokenState.value = TokenState.REMOVED
        Log.d(TAG, "removeUserToken: User Token Removed!")
    }

    fun setUserToken(activity: Activity, userToken: String){
        val pref = activity.getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        pref.edit().putString(USER_TOKEN, userToken).apply()
        userTokenState.value = TokenState.STORED
    }

    fun updateTokenState(tokenState: TokenState){
        userTokenState.value = tokenState
    }
}