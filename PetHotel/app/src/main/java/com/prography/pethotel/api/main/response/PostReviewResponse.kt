package com.prography.pethotel.api.main.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostReviewResponse(
    val data: PostReviewResponseData,
    val message: String,
    val status: Int
) : Parcelable

@Parcelize
data class PostReviewResponseData(
    val id: Int,
    val userId: Int,
    val hotelId: Int,
    val content: String,
    val rating: Int,
    val createdAt: String,
    val updatedAt: String
) : Parcelable

//{"status":201,"message":"Success Hotel Review Creation","data":{"id":2,"rating":5,"createdAt":"2020-07-21T01:34:30.000Z","updatedAt":"2020-07-21T01:34:30.308Z","content":"Hello World!","userId":65,"hotelId":53}}