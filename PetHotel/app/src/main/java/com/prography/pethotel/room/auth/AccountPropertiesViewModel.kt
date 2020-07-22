package com.prography.pethotel.room.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.room.entities.User
import com.prography.pethotel.room.entities.UserAndAllPets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "AccountPropertiesViewMo"

class AccountPropertiesViewModel(
    val accountPropertiesDao: AccountPropertiesDao,
    application: Application
) : AndroidViewModel(application){

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val userProperty = MutableLiveData<User>()
    val userAndPetProperty = MutableLiveData<List<UserAndAllPets>>()


    /* 유저 정보를 데이터베이스에 저장한다. */
    fun insertUserToDb(user : User){
        CoroutineScope(Dispatchers.IO).launch{
            val result = accountPropertiesDao.insertUser(user)
            Log.d(TAG, "insertUserToDb: $result")
        }
    }

    /* 펫에 대한 정보를 펫 테이블에 넣는다. 유저 아이디를 필드로 갖고 있다. */
    fun insertPetToDb(pet : Pet){
        CoroutineScope(Dispatchers.IO).launch {
            val result = accountPropertiesDao.insertPetToUser(pet)
            Log.d(TAG, "insertPetToDb: $result")
        }
    }

    /* 유저 정보를 테이블에서 불러온다 */
    fun fetchUser(userId : Int){
        coroutineScope.launch {
            val result = getUserInfoFromDb(userId)
            userProperty.value = result
        }
    }

    /* 유저와 유저의 아이디를 참조하는 모든 펫들을 불러온다. */
    fun fetchUserAndPets(){
        coroutineScope.launch {
            val result = getUserAndPetsFromDb()
            userAndPetProperty.value = result
        }
    }

    fun deleteUserProperties(userId : Int){
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO){
                accountPropertiesDao.deleteUserInfo(userId)
            }
            Log.d(TAG, "deleteUserProperties: DELETE User info result => $result")
        }
    }

    private suspend fun getUserInfoFromDb(userId : Int) =
        withContext(Dispatchers.IO){
            accountPropertiesDao.getUserInfo(userId)
        }

    private suspend fun getUserAndPetsFromDb() =
        withContext(Dispatchers.IO){
            accountPropertiesDao.getUserAndAllPets()
        }
}