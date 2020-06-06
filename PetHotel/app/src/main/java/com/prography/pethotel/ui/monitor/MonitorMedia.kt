package com.prography.pethotel.ui.monitor

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MonitorMedia(
    val mediaUrl : String,
    val mediaComment : String,
    val createdAt : String
) : Parcelable
