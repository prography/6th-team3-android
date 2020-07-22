package com.prography.pethotel.api.main.request

data class ReviewPostBody(
    val `data`: ReviewPostData,
    val userId: Int
)

data class ReviewPostData(
    val content: String,
    val rating: Int
)