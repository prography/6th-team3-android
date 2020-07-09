package com.prography.pethotel.api.main.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelImageResponse(
    val display: Int,
    val items: List<HotelImage>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
) : Parcelable

@Parcelize
data class HotelImage(
    val link: String,
    val sizeheight: String,
    val sizewidth: String,
    val thumbnail: String,
    val title: String
) : Parcelable