package com.prography.pethotel.room.entities

import androidx.annotation.ColorInt
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "hotel")
data class Hotel(
    @PrimaryKey @ColumnInfo(name = "id") val hotelId : Int,
    @ColumnInfo(name = "createdAt") val createdAt : String,
    @ColumnInfo(name = "updatedAt") val updatedAt : String,
    @ColumnInfo(name = "name") val hotelName : String,
    @ColumnInfo(name = "description") val description : String ?= null,
    @ColumnInfo(name = "address") val address: String ?= null,
    @ColumnInfo(name = "addressDetail") val addressDetail: String ?= null,
    @ColumnInfo(name = "zipcode") val zipcode: String ?= null,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "weekOpenTime") val weekOpenTime: String ?= null,
    @ColumnInfo(name = "weekCloseTime") val weekCloseTime: String ?= null,
    @ColumnInfo(name = "satOpenTime") val satOpenTime: String ?= null,
    @ColumnInfo(name = "satCloseTime") val satCloseTime: String ?= null,
    @ColumnInfo(name = "sunOpenTime") val sunOpenTime: String ?= null,
    @ColumnInfo(name = "sunCloseTime") val sunCloseTime: String ?= null,
    @ColumnInfo(name = "phoneNumber") val phoneNumber : String ?= null,
    @ColumnInfo(name = "monitorAvailable") val monitorAvailable : Boolean ?= false,
    @ColumnInfo(name = "isNeuteredOnly") val isNeuteredOnly : Boolean ?= false,
    @ColumnInfo(name = "maxDogSize") val maxDogSize : Int?= null,
    @ColumnInfo(name = "pageLink") val pageLink : String?= null,
    @ColumnInfo(name = "mediumCriteria") val mediumCriteria : Int?= null,
    @ColumnInfo(name = "largeCriteria") val largeCriteria : Int?= null,
    @ColumnInfo(name = "prices") val prices : ArrayList<Price>?= null,
    @ColumnInfo(name = "hotelImage") val hotelImage : String ?= null
)

@Entity(
    tableName = "price",
    foreignKeys = [
    ForeignKey(entity = Hotel::class,
    parentColumns = ["id"],
    childColumns = ["hotelId"],
        onDelete = CASCADE
    )
    ]
)
data class Price(
    @PrimaryKey @ColumnInfo(name = "id") val priceId : Int,
    @ColumnInfo(name = "hotelId", index = true) val hotelId : Int,
    @ColumnInfo(name = "createdAt") val createdAt: String,
    @ColumnInfo(name = "updatedAt") val updatedAt: String,
    @ColumnInfo(name = "day") val day : String? = null,
    @ColumnInfo(name = "weight") val weight : Int? = null,
    @ColumnInfo(name = "size") val size : String? = null,
    @ColumnInfo(name = "price") val price : Int? = null
)

@Entity(tableName = "hotelLike",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = CASCADE
        )
    ])
data class HotelLike(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id : Int, //hotel Id 를 넣는다

    @ColumnInfo(name = "userId", index = true)
    val userId : Int
)

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name= "id")  val userId : Int,
    @ColumnInfo(name = "name") val userName : String?= null,
    @ColumnInfo(name = "phoneNumber") val phoneNumber : String?=null,
    @ColumnInfo(name = "email") val email : String?= null,
    @ColumnInfo(name = "profileImage") val profileImage : String?=null,
    @ColumnInfo(name = "userToken") val userToken : String
)


@Entity(tableName = "pet",
        foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = CASCADE
        )
]
)
data class Pet(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name= "id") val petId : Int,
    @ColumnInfo(name = "name") val petName : String,
    @ColumnInfo(name = "year") val petBirthYear : Int?= null,
    @ColumnInfo(name = "dog_reg_no") val dogRegisterNo : String?= null, //String?
    @ColumnInfo(name = "rfid_cd") val rfidCardNo : String?= null,
    @ColumnInfo(name = "gender") val gender : String,
    @ColumnInfo(name = "kind") val kind : String,
    @ColumnInfo(name = "is_neutered") val isNeutered : Boolean,
    @ColumnInfo(name = "ownerId", index = true) val ownerId : Int,
    @ColumnInfo(name = "profile_path") val profileImagePath : String?= null
 )


@Entity(
    tableName = "reservation",
    foreignKeys = [
    ForeignKey(entity = Pet::class,
    parentColumns = ["id"],
    childColumns = ["petId"]
    ),
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )
    ]
)
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val reservationId : Int,

    @ColumnInfo(name = "petId")
    val petId : Int,

    @ColumnInfo(name = "userId", index = true)
    val userId : Int,
    //호텔 테이블이 비어있어서 외래 키 오류가 계속 나므로
    //임시적으로 호텔 아이디가 아니라, 호텔 이름과 주소를
    //명시한다.
    @ColumnInfo(name =  "hotelName")
    val hotelName : String,

    @ColumnInfo(name = "hotelAddress")
    val hotelAddress : String,

    @ColumnInfo(name = "checkInTime")
    val checkInTime : String,

    @ColumnInfo(name = "checkInDate")
    val checkInDate : String,

    @ColumnInfo(name = "checkOutTime")
    val checkOutTime : String,

    @ColumnInfo(name = "checkOutDate")
    val checkOutDate : String,

    @ColumnInfo(name = "request")
    val request : String
)


class UserAndAllPets{
    @Embedded
    var user : User? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    var pets : List<Pet> = ArrayList()
}

class Converters{

    @TypeConverter
    fun fromPrice(value : ArrayList<Price>?) : String{
        val gson = Gson()
        if(value == null){
            return ""
        }
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPriceFromString(value : String?) : ArrayList<Price>?{
        val gson = Gson()
        if(value == null){
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<Price>?>() {}.type
        return gson.fromJson(value, listType)
    }
}