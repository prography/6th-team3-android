package com.prography.pethotel.api.main.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Parcelize
@Root(name = "response")
data class PetNumberResponse @JvmOverloads constructor(
    @field: Element(name = "body")
    val body: Body,

    @field: Element(name = "header")
    val header: Header
) : Parcelable


//@Parcelize
//data class Response(
//
//) : Parcelable

@Parcelize
@Root(name = "header")
class Header @JvmOverloads constructor(
    @field: Element(name = "resultCode")
    val resultCode: String,
    @field: Element(name = "resultMsg")
    val resultMsg: String
) : Parcelable

@Parcelize
@Root(name = "body")
data class Body @JvmOverloads constructor(
    @field: Element(name = "item")
    val item: Item
) : Parcelable

@Parcelize
@Root(name = "item")
data class Item @JvmOverloads constructor(
    @field: Element(name = "aprGbNm")
    val aprGbNm: String,
    @field: Element(name = "dogNm")
    val dogNm: String,
    @field: Element(name = "dogRegNo")
    val dogRegNo: String,
    @field: Element(name = "kindNm")
    val kindNm: String,
    @field: Element(name = "neuterYn")
    val neuterYn: String,
    @field: Element(name = "officeTel")
    val officeTel: String,
    @field: Element(name = "orgNm")
    val orgNm: String,
    @field: Element(name = "rfidCd")
    val rfidCd: String,
    @field: Element(name = "sexNm")
    val sexNm: String
) : Parcelable