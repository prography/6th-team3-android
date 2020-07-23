package com.prography.pethotel.api.auth.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.File

@Parcelize
data class RegisterPetBody(
    @SerializedName("data")
    val `data`: List<PetData>
) : Parcelable

@Parcelize
data class PetData(
    @SerializedName("petName")
    val petName: String,

    @SerializedName("registerNumber")
    val registerNumber: String,

    @SerializedName("year")
    val year: Int,

    val breed : String,
    val isNeutered : Boolean,
    val gender : String,
    val rfidCode : String
) : Parcelable