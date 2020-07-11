package com.prography.pethotel.api.main.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelPrice(
    val createdAt: String,
    val day: String,
    val hotelId: Int,
    val id: Int,
    val price: Int,
    val size: String,
    val updatedAt: String,
    val weight: Int
) : Parcelable