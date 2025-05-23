package com.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val phone: String,
    val status: String
    )

@Serializable
data class RegisterResponseRemote(
    val token: String
)