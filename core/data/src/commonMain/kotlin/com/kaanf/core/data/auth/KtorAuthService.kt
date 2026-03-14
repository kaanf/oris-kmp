package com.kaanf.core.data.auth

import com.kaanf.core.data.dto.request.EmailRequest
import com.kaanf.core.data.dto.request.RegisterRequest
import com.kaanf.core.data.dto.request.UsernameRequest
import com.kaanf.core.data.networking.get
import com.kaanf.core.data.networking.post
import com.kaanf.core.domain.auth.AuthService
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.Result
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient
): AuthService {
    override suspend fun register(
        email: String, username: String, password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(
                email = email, username = username, password = password
            )
        )
    }

    override suspend fun resendVerificationMail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend-verification",
            body = EmailRequest(email),
        )
    }

    override suspend fun isUsernameExists(username: String): Result<Boolean, DataError.Remote> {
        return httpClient.post(
            route = "/auth/username-exists",
            body = UsernameRequest(username)
        )
    }

    override suspend fun isEmailExists(email: String): Result<Boolean, DataError.Remote> {
        return httpClient.post(
            route = "/auth/email-exists",
            body = EmailRequest(email),
        )
    }

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = "/auth/verify",
            queryParams = mapOf("token" to token)
        )
    }
}