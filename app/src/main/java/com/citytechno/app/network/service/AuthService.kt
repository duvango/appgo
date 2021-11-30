package com.citytechno.app.network.service

import com.citytechno.app.network.service.dto.LoginDto
import com.citytechno.app.network.service.dto.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface AuthService {
    @POST("auth")
    suspend fun login(@Body loginDto: LoginDto): Response<TokenDto>
}