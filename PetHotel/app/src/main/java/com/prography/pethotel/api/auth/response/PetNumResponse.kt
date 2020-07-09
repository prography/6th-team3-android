package com.prography.pethotel.api.auth.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name = "response")
data class PetNumResponse (
    @Element
    val header: Header? = null,
    @Element
    val body : Body? = null
)

@Xml(name = "header")
data class Header(
    @PropertyElement
    val resultCode: String? = null,

    @PropertyElement
    val resultMsg: String? = null
)


@Xml(name = "body")
data class Body (
    @Element
    val item: Item? = null
)


@Xml(name = "item")
data class Item(
    @PropertyElement
    val aprGbNm: String? = null,

    @PropertyElement
    val dogNm: String? = null,

    @PropertyElement
    val dogRegNo: String? = null,

    @PropertyElement
    val kindNm: String? = null,

    @PropertyElement
    val neuterYn: String? = null,

    @PropertyElement
    val officeTel: String? = null,

    @PropertyElement
    val orgNm: String? = null,

    @PropertyElement
    val rfidCd: String? = null,

    @PropertyElement
    val sexNm: String? = null
)