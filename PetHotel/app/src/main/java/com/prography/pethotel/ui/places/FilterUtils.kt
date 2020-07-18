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
    hotels.sortWith(object : Comparator<HotelData>{
        override fun compare(o1: HotelData?, o2: HotelData?): Int {
            if(o1 == null || o2 == null){
                return 0
            }
            if(o1.prices.isNullOrEmpty()){
                return 1
            }
            if(o2.prices.isNullOrEmpty()){
                return -1
            }
            if(o1.prices[0].price < o2.prices[0].price){
                return -1
            }else if(o1.prices[0].price > o2.prices[0].price){
                return 1
            }
            return 0
        }
    })
    return hotels
}

