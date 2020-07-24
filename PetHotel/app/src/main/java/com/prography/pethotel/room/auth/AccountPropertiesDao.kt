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

    /* REPLACE 하면 pet table 이 같이 삭제되므로
    유저는 반드시 한 번 만 insert 되어야 한다. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user : User) : Long

    @Query("SELECT * FROM pet WHERE ownerId = :userId")
    suspend fun getPetByUserId(userId : Int) : List<Pet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetToUser(pet : Pet) : Long

    @Query("DELETE FROM user WHERE userToken = :userToken")
    suspend fun deleteUserInfo(userToken: String) : Int
}