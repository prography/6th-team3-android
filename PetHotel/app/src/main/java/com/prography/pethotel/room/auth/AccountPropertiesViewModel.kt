package com.prography.pethotel.room.auth

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.api.auth.response.UserToken
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.room.entities.User
import com.prography.pethotel.room.entities.UserAndAllPets
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.lang.Exception


private const val TAG = "AccountPropertiesViewMo"

class AccountPropertiesViewModel(
    val accountPropertiesDao: AccountPropertiesDao,
    application: Application
) : AndroidViewModel(application){

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val userProperty = MutableLiveData<User>()
    val userAndPetProperty = MutableLiveData<List<UserAndAllPets>>()
    val petListResult = MutableLiveData<List<Pet>>()
    val insertUserResponse = MutableLiveData<String>()

    /* 유저 정보를 데이터베이스에 저장한다. */
    fun insertUserToDb(user : User){
        CoroutineScope(Dispatchers.Main).launch{
            try{
                val result = withContext(Dispatchers.IO){
                    accountPropertiesDao.insertUser(user)
                }
                Log.d(TAG, "insertUserToDb: $result")
                insertUserResponse.value = "success"
            }catch (e: SQLiteConstraintException){
                Log.d(TAG, "insertUserToDb: ${e.printStackTrace()}")
                insertUserResponse.value = "constraint_failed"
            }
            catch (e : Exception){
                insertUserResponse.value = "exception"
            }
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
    fun fetchUser(userToken: String){
        coroutineScope.launch {
            val result = getUserInfoFromDb(userToken)
            userProperty.value = result
        }
    }

    fun fetchPetByUserId(userId : Int){
        coroutineScope.launch {
            val result = getPetList(userId)
            petListResult.value = result
        }
    }

    /* 유저와 유저의 아이디를 참조하는 모든 펫들을 불러온다. */
    fun fetchUserAndPets(){
        coroutineScope.launch {
            val result = getUserAndPetsFromDb()
            userAndPetProperty.value = result
        }
    }

    fun deleteUserProperties(userToken: String){
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO){
                accountPropertiesDao.deleteUserInfo(userToken)
            }
            Log.d(TAG, "deleteUserProperties: DELETE User info result => $result")
            if(result > 0){
                userProperty.value = null
            }
        }
    }

    private suspend fun getUserInfoFromDb(userToken: String) =
        withContext(Dispatchers.IO){
            accountPropertiesDao.getUserInfo(userToken)
        }

    private suspend fun getUserAndPetsFromDb() =
        withContext(Dispatchers.IO){
            accountPropertiesDao.getUserAndAllPets()
        }

    private suspend fun getPetList(userId : Int) =
        withContext(Dispatchers.IO){
            accountPropertiesDao.getPetByUserId(userId)
        }
}