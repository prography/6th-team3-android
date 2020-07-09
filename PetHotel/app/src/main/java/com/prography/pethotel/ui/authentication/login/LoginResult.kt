package com.prography.pethotel.ui.authentication.login

import com.prography.pethotel.api.auth.response.UserToken

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: UserToken? = null,
        val error: Int? = null
)