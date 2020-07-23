package com.prography.pethotel.api.main.request

data class ReservationPostBody (
    val userId : Int,
    val data : List<ReservationPostData>
)

data class ReservationPostData(
    val petId : Int,
    val startDate : String,
    val endDate : String,
    val pickupTime : String,
    val request : String
)

//{
//	"userId": 1,
//	"data":[
//		{
//		    "petId": 1,
//			"startDate": "2020-07-24T15:00:00.000Z",
//			"endDate": "2020-07-25T10:00:00.000Z",
//			"pickupTime": "10:00",
//		    "request": "요청사항"
//		}
//	]
//}
