package com.prography.pethotel.room

import androidx.room.*
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.room.entities.User
import com.prography.pethotel.room.entities.UserAndAllPets

@Dao
interface AccountPropertiesDao {

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserInfo(userId : Int) : User

    @Transaction
    @Query("SELECT * FROM user")
    suspend fun getUserAndAllPets() : List<UserAndAllPets>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAndReplace(user : User) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetToUser(pet : Pet) : Long

}