package com.prography.pethotel.ui.reservation

import java.util.*


class ReservationDateTime (
    var enterTime: String = DateTimeHelper().getTimeMessage(),
    var exitTime: String = DateTimeHelper().getTimeMessage(),
    var enterDate: String = DateTimeHelper().getDateMessage(),
    var exitDate: String = DateTimeHelper().getDateMessage()
)


class DateTimeHelper{

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    fun getDateMessage() : String{
        return "${year}년 ${month}월 ${day}일"
    }

    fun getTimeMessage() : String{
        return "${hour}시 ${minute}분"
    }
}