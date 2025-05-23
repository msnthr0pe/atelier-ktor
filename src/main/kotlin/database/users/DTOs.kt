package com.database.users

import kotlinx.serialization.Serializable

@Serializable
class UserDTO (
    val phone: String,
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)

@Serializable
data class TextDTO(val info: String)

@Serializable
data class PasswordDTO(
    val email: String,
    val password: String
)