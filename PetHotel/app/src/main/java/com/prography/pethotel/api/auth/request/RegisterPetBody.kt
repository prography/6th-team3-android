package com.prography.pethotel.api.auth.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import okhttp3.MultipartBody
import java.io.File

//@Parcelize
//data class RegisterPetBody(
//    @SerializedName("data")
//    val `data`: List<PetData>
//) : Parcelable

data class PetData(
    val petName: String,

    val registerNumber: String,

    val birthYear: Int,

    val breed : String,
    val isNeutered : Boolean,
    val gender : String,
    val rfidCode : String,
    val petProfilePhoto : MultipartBody.Part ?= null
)