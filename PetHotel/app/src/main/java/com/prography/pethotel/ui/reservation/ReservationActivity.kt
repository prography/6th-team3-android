package com.prography.pethotel.ui.reservation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.utils.RESERVE_HOTEL_INFO_KEY

private const val TAG = "ReservationActivity"
@Suppress("DEPRECATION")
class ReservationActivity : AppCompatActivity() {

    lateinit var reservationViewModel: ReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        reservationViewModel = ViewModelProviders.of(this)[ReservationViewModel::class.java]

        val hotelData = intent.extras?.get(RESERVE_HOTEL_INFO_KEY)
        Log.d(TAG, "onCreate: $hotelData")
        reservationViewModel.setReservationHotelInfo((hotelData as HotelData?)!!)
    }
}