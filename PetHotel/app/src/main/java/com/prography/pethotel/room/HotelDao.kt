package com.prography.pethotel.room

import androidx.room.*
import com.prography.pethotel.room.entities.Hotel
import com.prography.pethotel.room.entities.HotelLike


@Dao
interface HotelDao {

    @Query("SELECT * FROM hotel")
    suspend fun getAllHotels() : List<Hotel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotel(hotel : Hotel) : Long

    @Query("DELETE FROM hotel WHERE id = :hotelId")
    suspend fun deleteHotelById(hotelId: Int) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteHotel(hotelLike: HotelLike) : Long

    @Query("DELETE FROM hotelLike WHERE id = :hotelId")
    suspend fun deleteFavoriteHotelById(hotelId : Int) : Int

    @Query("SELECT * FROM hotel WHERE id = :hotelId")
    suspend fun getAllLikedHotels(hotelId: Int) : List<Hotel>

    @Query("SELECT * FROM hotelLike")
    suspend fun getAllLikedHotelsId() : List<HotelLike>
}