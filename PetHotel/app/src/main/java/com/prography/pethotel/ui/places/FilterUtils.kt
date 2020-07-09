package com.prography.pethotel.ui.places

import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.ui.places.FilterType.*

enum class FilterType{
    POPULARITY, DISTANCE, PRICE
}

fun filterBy(filterType : FilterType, hotels : ArrayList<HotelData>): ArrayList<HotelData> {

    return when (filterType) {
        POPULARITY -> {
            sortListByPopularity(hotels)
        }
        DISTANCE -> {
            sortListByDistance(hotels)
        }
        PRICE -> {
            sortListByPrice(hotels)
        }
    }
}

fun sortListByPopularity(hotels: ArrayList<HotelData>)
: ArrayList<HotelData>{
    //TODO Sorting 한 뒤에 반환하기.
    return hotels
}

fun sortListByDistance(hotels: ArrayList<HotelData>)
: ArrayList<HotelData>{
    //TODO Sorting 한 뒤에 반환하기.
    return hotels
}

fun sortListByPrice(hotels: ArrayList<HotelData>)
: ArrayList<HotelData>{
    //TODO Sorting 한 뒤에 반환하기.
    return hotels
}

