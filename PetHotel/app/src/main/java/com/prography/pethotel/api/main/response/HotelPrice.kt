package com.prography.pethotel.api.main.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelPrice(
    val createdAt: String,
    val day: String ?= null,
    val id: Int,
    val hotelId: Int,
    val price: Int ?= Int.MAX_VALUE,
    val size: String ?= null,
    val updatedAt: String?= null,
    val weight: Int?= null
) : Parcelable