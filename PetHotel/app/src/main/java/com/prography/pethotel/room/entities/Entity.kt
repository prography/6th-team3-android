package com.prography.pethotel.room.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.math.BigInteger

@Entity(tableName = "hotel")
class Hotel(
    @PrimaryKey @ColumnInfo(name = "id") val hotelId : Int,
    @ColumnInfo(name = "createdAt") val createdAt : String,
    @ColumnInfo(name = "updatedAt") val updatedAt : String,
    @ColumnInfo(name = "name") val hotelName : String,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "addressDetail") val addressDetail: String,
    @ColumnInfo(name = "zipcode") val zipcode: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "weekOpenTime") val weekOpenTime: String,
    @ColumnInfo(name = "weekCloseTime") val weekCloseTime: String,
    @ColumnInfo(name = "satOpenTime") val satOpenTime: String,
    @ColumnInfo(name = "satCloseTime") val satCloseTime: String,
    @ColumnInfo(name = "sunOpenTime") val sunOpenTime: String,
    @ColumnInfo(name = "sunCloseTime") val sunCloseTime: String,
    @ColumnInfo(name = "weekPrice") val weekPrice: Int,
    @ColumnInfo(name = "satPrice") val satPrice: Int,
    @ColumnInfo(name = "sunPrice") val sunPrice: Int,
    @ColumnInfo(name = "phoneNumber") val phoneNumber : String,
    @ColumnInfo(name = "monitorAvailable") val monitorAvailable : Boolean,
    @ColumnInfo(name = "isNeuteredOnly") val isNeuteredOnly : Boolean,
    @ColumnInfo(name = "maxDogSize") val maxDogSize : Int,
    @ColumnInfo(name = "pageLink") val pageLink : String
)

@Entity(
    tableName = "price",
    foreignKeys = [
    ForeignKey(entity = Hotel::class,
    parentColumns = ["id"],
    childColumns = ["hotelId"]
    )
    ]
)
class Price(
    @PrimaryKey @ColumnInfo(name = "id") val priceId : Int,
    @ColumnInfo(name = "hotelId") val hotelId : Int,
    @ColumnInfo(name = "createdAt") val createdAt: String,
    @ColumnInfo(name = "updatedAt") val updatedAt: String,
    @ColumnInfo(name = "day") val day : String? = null,
    @ColumnInfo(name = "weight") val weight : Int? = null,
    @ColumnInfo(name = "size") val size : Size? = null,
    @ColumnInfo(name = "price") val price : Int? = null
)

@Entity(tableName = "size")
enum class Size{
    SMALL, MEDIUM, LARGE
}

@Entity(tableName = "hotelLike",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"])
    ])
class HotelLike(
    @PrimaryKey @ColumnInfo(name = "id") val hotelLikeId : Int,
    @ColumnInfo(name = "createdAt") val createdAt : String,
    @ColumnInfo(name = "updatedAt") val updatedAt : String,
    @ColumnInfo(name = "userId") val userId : Int
    )

@Entity(tableName = "user")
class User(
    @PrimaryKey @ColumnInfo(name= "id") val userId : Int,
    @ColumnInfo(name = "name") val userName : String,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "password") val password : String,
    @ColumnInfo(name = "provider") val provider : String,
    @ColumnInfo(name = "accessToken") val accessToken : String,
    @ColumnInfo(name = "refreshToken") val refreshToken : String,
    @ColumnInfo(name = "createdAt") val createdAt : String,
    @ColumnInfo(name = "updatedAt") val updatedAt : String
)


@Entity(tableName = "pet",
        foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = CASCADE
        )
])
class Pet(
    @PrimaryKey @ColumnInfo(name= "id") val petId : Int,
    @ColumnInfo(name = "name") val petName : String,
    @ColumnInfo(name = "year") val petBirthYear : Int,
    @ColumnInfo(name = "dog_reg_no") val dogRegisterNo : BigInteger, //String?
    @ColumnInfo(name = "rfid_cd") val rfidCardNo : String,
    @ColumnInfo(name = "gender") val gender : Int,
    @ColumnInfo(name = "kind") val kind : String,
    @ColumnInfo(name = "neuter_year") val neuteredYear : Int,
    @ColumnInfo(name = "createdAt") val createdAt : String,
    @ColumnInfo(name = "updatedAt") val updatedAt : String,
    @ColumnInfo(name = "ownerId") val userId : Int
 )


class UserAndAllPets{
    @Embedded
    var user : User? = null

    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    var pets : List<Pet> = ArrayList()
}
// DAO 클래스 안에서 아래와 같이 쿼리를 날린다.
//@Transaction
//@Query(“SELECT * FROM Users”)
//List<UserAndAllPets> getUsers();

