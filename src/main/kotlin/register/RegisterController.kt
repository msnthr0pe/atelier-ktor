package com.register

import com.database.tokens.TokenDTO
import com.database.tokens.Tokens
import com.database.users.UserDTO
import com.database.users.Users
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.UUID

class RegisterController (val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        val userDTO = Users.fetchUser(registerReceiveRemote.email)
        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()
            try {
                Users.insert(
                    UserDTO(
                        email = registerReceiveRemote.email,
                        password = registerReceiveRemote.password,
                        name = registerReceiveRemote.name,
                        surname = registerReceiveRemote.surname,
                        phone = registerReceiveRemote.phone
                    )
                )
            } catch (e: ExposedSQLException) {
                e.printStackTrace() // это покажет реальную причину ошибки
                call.respond(HttpStatusCode.Conflict, "SQL error: ${e.localizedMessage}")
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, "Unexpected error: ${e.localizedMessage}")
            }
            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(),
                    login = registerReceiveRemote.email,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }
    }

}