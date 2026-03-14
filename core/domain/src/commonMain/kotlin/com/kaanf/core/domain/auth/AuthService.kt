package com.kaanf.core.domain.auth

import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.Result

interface AuthService {
    suspend fun register(
        email: String, username: String, password: String
    ): EmptyResult<DataError.Remote>

    suspend fun resendVerificationMail(email: String): EmptyResult<DataError.Remote>

    suspend fun isUsernameExists(username: String): Result<Boolean, DataError.Remote>

    suspend fun isEmailExists(email: String): Result<Boolean, DataError.Remote>

    suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote>
}