package com.prography.pethotel.room.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.prography.pethotel.api.main.response.HotelReviewData
import com.prography.pethotel.room.entities.Hotel
import com.prography.pethotel.room.entities.HotelLike
import com.prography.pethotel.room.entities.Reservation
import com.prography.pethotel.room.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "MainDbViewModel"
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

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val likedHotels = MutableLiveData<List<Hotel>>()
    val userReviewList = MutableLiveData<List<HotelReviewData>>()

    fun insertFavoriteHotel(hotelLike: HotelLike){
        CoroutineScope(Dispatchers.IO).launch {
            val result = mainDao.insertFavoriteHotel(hotelLike = hotelLike)
            Log.d(TAG, "insertFavoriteHotel: $result")
        }
    }

    fun getAllLikedHotels(){
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO){
                mainDao.getAllLikedHotels()
            }
            likedHotels.value = result
        }
    }

    fun deleteFavoriteHotelById(hotelId : Int){
        CoroutineScope(Dispatchers.IO).launch {
            val result = mainDao.deleteFavoriteHotelById(hotelId)
            Log.d(TAG, "deleteFavoriteHotelById: $result")
        }
    }

    fun insertHotelReview(hotelReviewData: HotelReviewData){
        CoroutineScope(Dispatchers.IO).launch {
            val result = mainDao.insertHotelReview(hotelReviewData)
            Log.d(TAG, "insertHotelReview: $result")
        }
    }

    fun getHotelReviewByUserId(userId : Int){
        CoroutineScope(Dispatchers.Main).launch{
            val result =
                withContext(Dispatchers.IO){mainDao.getHotelReviewByUserId(userId)}
            Log.d(TAG, "getHotelReviewByUserId: $result")
            userReviewList.value = result
        }
    }

    fun insertReservation(reservation: Reservation){
        CoroutineScope(Dispatchers.IO).launch {
            val result = mainDao.insertReservation(reservation)
            Log.d(TAG, "insertReservation: $result")
        }
    }
}