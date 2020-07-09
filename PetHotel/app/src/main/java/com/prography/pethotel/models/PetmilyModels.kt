package com.prography.pethotel.models
//
//data class Hotel(
//    val id               : Int,
//    val name             : String?,
//    val description      : String?,
//    val address          : String?,
//    val addressDetail    : String?,
//    val zipcode          : String?,
//    val latitude         : Float?,
//    val longitude        : Float?,
//    val weekOpenTime     : String?,
//    val weekCloseTime    : String?,
//    val satOpenTime      : String?,
//    val satCloseTime     : String?,
//    val sunOpenTime      : String?,
//    val sunCloseTime     : String?,
//    val phoneNumber      : String?,
//    val monitorAvailable : Boolean?,
//    val isNeuteredOnly   : Boolean?,
//    val maxDogSize       : Int?,
//    val pageLink         : String?,
//    val mediumCriteria   : Int?,
//    val largeCriteria    : Int?,
////    val monitorings      : Monitoring[]
////    val services         : Service[]
//    val prices           : ArrayList<Price>
//)
//
//data class Monitoring (
//    val id        : Int,
//    val hotelId   : Int,
//    val name      : String?,
//    val hotel     : Hotel
//)
//
//data class Service (
//    val id        : Int,
//    val hotelId   : Int,
//    val name      : String?,
//    val hotel     : Hotel
//)
//
//data class Price (
//    val id        : Int,
//    val hotelId   : Int,
//    val day       : String?,
//    val weight    : Int?,
//    val size      : Size?,
//    val price     : Int?,
//    val hotel     : Hotel
//)
//
//data class Photo (
//    val id        : Int,
//    val url       : String?,
//    val target    : Target?,
//    val targetId  : Int?
//)
//
//data class User (
//    val id             : Int,
//    val name           : String?,
//    val email          : String,
////    val password       : String?,
//    val provider       : Provider?,
//    val accessToken    : String?,
//    val refreshToken   : String?,
//    val phoneNumber    : String?,
//    val pets           : ArrayList<Pet>,
//    val favoriteHotels : ArrayList<Hotel>,
//    val Reservation    : ArrayList<Reservation>,
//    val Review : ArrayList<Review>
//)
//
//model Pet {
//    id           Int           @default(autoincrement()) @id
//    userId       Int?          @map("user_id")
//    createdAt    DateTime      @default(now()) @map("created_at")
//    updatedAt    DateTime?     @map("updated_at") @updatedAt
//    name         String?
//    year         Int?
//    weight       Int?
//    registerNum  String?       @map("register_num")
//    rfidCode     String?       @map("rfid_cd")
//    breed        String?
//    isNeutered   Boolean?      @map("is_neutered")
//    gender       Gender?
//    owner        User?         @relation(fields: [userId], references: [id])
//    reservations Reservation[]
//    @@map("pet")
//}
//
//model Reservation {
//    id         Int       @default(autoincrement()) @id
//    userId     Int       @map("user_id")
//    hotelId    Int       @map("hotel_id")
//    petId      Int       @map("pet_id")
//    createdAt  DateTime  @default(now()) @map("created_at")
//    updatedAt  DateTime? @map("updated_at") @updatedAt
//    startDate  DateTime? @map("start_date")
//    endDate    DateTime? @map("end_date")
//    pickupTime String?   @map("pick_up_time")
//    request    String?
//    user       User      @relation(fields: [userId], references: [id])
//    hotel      Hotel     @relation(fields: [hotelId], references: [id])
//    pet        Pet       @relation(fields: [petId], references: [id])
//    @@map("reservation")
//}
//
//model Review {
//    id        Int       @default(autoincrement()) @id
//    rating    Int?
//    createdAt DateTime  @default(now()) @map("created_at")
//    updatedAt DateTime? @map("updated_at") @updatedAt
//    content   String?
//    userId    Int       @map("user_id")
//    hotelId   Int       @map("hotel_id")
//    user      User      @relation(fields: [userId], references: [id])
//    hotel     Hotel     @relation(fields: [hotelId], references: [id])
//    @@map("review")
//}
//
//enum Gender {
//    MALE    @map("수컷")
//    FEMAIL  @map("암컷")
//    @@map("gender")
//}
//
//enum Target {
//    PET    @map("pet")
//    USER   @map("user")
//    HOTEL  @map("hotel")
//    @@map("target")
//}
//
//enum Size {
//    SMALL   @map("small")
//    MEDIUM  @map("medium")
//    LARGE   @map("large")
//    @@map("size")
//}
//
//enum Provider {
//    KAKAO     @map("kakao")
//    NAVER     @map("naver")
//    GOOGLE    @map("google")
//    FACEBOOK  @map("facebook")
//    @@map("provider")
//}