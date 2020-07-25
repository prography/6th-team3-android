package com.prography.pethotel.api.main.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.prography.pethotel.room.entities.Hotel
import com.prography.pethotel.room.entities.User
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GetReviewResponse(
    val `data`: List<HotelReviewData>,
    val message: String,
    val status: Int
) : Parcelable

@Entity(tableName = "hotel_review",
    foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )
    ]
)
@Parcelize
data class HotelReviewData(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name= "hotelName") val hotelName : String,
    @ColumnInfo(name = "content") val content: String?= null,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "createdAt") val createdAt: String,
    @ColumnInfo(name = "updatedAt") val updatedAt: String
) : Parcelable

//{"status":201,"message":"Success reviews get",
// "data":[
// {"id":1,"rating":5,"createdAt":"2020-07-21T01:34:01.000Z","updatedAt":"2020-07-21T01:34:01.458Z","content":null,"userId":65,"hotelId":53},
// {"id":2,"rating":5,"createdAt":"2020-07-21T01:34:30.000Z","updatedAt":"2020-07-21T01:34:30.308Z","content":"Hello World!","userId":65,"hotelId":53}
// ]
// }