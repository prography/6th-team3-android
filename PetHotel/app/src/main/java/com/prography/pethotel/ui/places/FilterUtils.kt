package com.prography.pethotel.ui.places

import com.prography.pethotel.models.Hotel
import com.prography.pethotel.ui.places.FilterType.*

enum class FilterType{
    POPULARITY, DISTANCE, PRICE
}

fun filterBy(filterType : FilterType, hotels : ArrayList<Hotel>): ArrayList<Hotel> {

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

fun sortListByPopularity(hotels: ArrayList<Hotel>)
: ArrayList<Hotel>{
    //TODO Sorting 한 뒤에 반환하기.
    return hotels
}

fun sortListByDistance(hotels: ArrayList<Hotel>)
: ArrayList<Hotel>{
    //TODO Sorting 한 뒤에 반환하기.
    return hotels
}

fun sortListByPrice(hotels: ArrayList<Hotel>)
: ArrayList<Hotel>{
    //TODO Sorting 한 뒤에 반환하기.
    return hotels
}

