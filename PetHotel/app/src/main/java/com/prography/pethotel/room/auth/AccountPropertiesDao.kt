package com.prography.pethotel.room.auth

import androidx.room.*
import com.prography.pethotel.api.auth.response.UserToken
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.room.entities.User
import com.prography.pethotel.room.entities.UserAndAllPets

@Dao
interface AccountPropertiesDao {

    @Query("SELECT * FROM user WHERE userToken = :userToken")
    suspend fun getUserInfo(userToken: String) : User

    @Transaction
    @Query("SELECT * FROM user")
    suspend fun getUserAndAllPets() : List<UserAndAllPets>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : User) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetToUser(pet : Pet) : Long

    @Query("DELETE FROM user WHERE userToken = :userToken")
    suspend fun deleteUserInfo(userToken: String) : Int
}