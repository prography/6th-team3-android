package com.prography.pethotel.api.main.response

data class UpdateReviewResponse(
    val `data`: UpdateReviewResponseData,
    val message: String,
    val status: Int
)


data class UpdateReviewResponseData(
    val content: String ?= null,
    val createdAt: String,
    val hotelId: Int,
    val id: Int,
    val rating: Int,
    val updatedAt: String,
    val userId: Int
)

//{
//  "status": 201,
//  "message": "Success Hotel edit",
//  "data": {
//    "id": 1,
//    "rating": 4,
//    "createdAt": "2020-07-21T01:34:01.000Z",
//    "updatedAt": "2020-07-21T01:47:10.241Z",
//    "content": null,
//    "userId": 65,
//    "hotelId": 53
//  }
//}