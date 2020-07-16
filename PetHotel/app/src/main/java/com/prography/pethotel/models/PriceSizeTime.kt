package com.prography.pethotel.models

data class PriceSizeTime(
    val day : String? = "all",
    val weight : DogWeight,
    val size : DogSize,
    val price : Price,
    val openTime : String? = "",
    val closeTime : String? = ""
)

data class DogWeight(
    val smallWeight : Int? = 0,
    val mediumWeight : Int? = 0,
    val largeWeight : Int? = 0
)

data class DogSize(
    val smallSize : String? = "",
    val mediumSize : String? = "",
    val largeSize : String? = ""
)

data class Price(
    val smallPrice : String?= "",
    val mediumPrice : String?="",
    val largePrice : String? = ""
)