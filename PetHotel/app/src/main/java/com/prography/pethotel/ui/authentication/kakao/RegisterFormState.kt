package com.prography.pethotel.ui.authentication.kakao

class RegisterFormState (
    val nicknameError : String? = null,
    val emailError : String? = null,
    val passwordError : String? = null,
    val phoneNumberError : String? = null,
    val isDataValid : Boolean = false
)