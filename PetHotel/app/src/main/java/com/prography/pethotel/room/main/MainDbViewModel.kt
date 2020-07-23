package com.prography.pethotel.room.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainDbViewModel(
    val mainDao: MainDao,
    application: Application
) : AndroidViewModel(application){

    //    @Query("SELECT * FROM hotel")
    //    suspend fun getAllHotels() : List<Hotel>
    //
    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //    suspend fun insertHotel(hotel : Hotel) : Long
    //
    //    @Query("DELETE FROM hotel WHERE id = :hotelId")
    //    suspend fun deleteHotelById(hotelId: Int) : Int
    //
    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //    suspend fun insertFavoriteHotel(hotelLike: HotelLike) : Long
    //
    //    @Query("DELETE FROM hotelLike WHERE id = :hotelId")
    //    suspend fun deleteFavoriteHotelById(hotelId : Int) : Int
    //
    //    @Query("SELECT * FROM hotel WHERE id = :hotelId")
    //    suspend fun getAllLikedHotels(hotelId: Int) : List<Hotel>
    //
    //    @Query("SELECT * FROM hotelLike")
    //    suspend fun getAllLikedHotelsId() : List<HotelLike>

}