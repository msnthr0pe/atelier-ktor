package com

import com.database.users.Users
import com.login.configureLoginRouting
import com.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}

fun Application.module() {
    Database.connect("jdbc:postgresql://localhost:5432/atelier",
        "org.postgresql.Driver",
        "postgres",
        "root")
    configureRegisterRouting()
    configureLoginRouting()
    configureSerialization()
    configureRouting()
}
