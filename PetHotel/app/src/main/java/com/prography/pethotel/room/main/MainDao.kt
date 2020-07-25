package com.prography.pethotel.room.main

import androidx.room.*
import com.prography.pethotel.api.main.response.HotelReviewData
import com.prography.pethotel.room.entities.Hotel
import com.prography.pethotel.room.entities.HotelLike
import com.prography.pethotel.room.entities.Reservation


@Dao
interface MainDao {

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

    @Query("SELECT * FROM hotel INNER JOIN hotelLike ON hotel.id = hotelLike.id")
    suspend fun getAllLikedHotels() : List<Hotel>

    @Query("SELECT * FROM hotelLike")
    suspend fun getAllLikedHotelsId() : List<HotelLike>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotelReview(hotelReviewData: HotelReviewData) : Long

    @Query("SELECT * FROM hotel_review WHERE userId = :userId")
    suspend fun getHotelReviewByUserId(userId : Int) : List<HotelReviewData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(reservation: Reservation) : Long

}