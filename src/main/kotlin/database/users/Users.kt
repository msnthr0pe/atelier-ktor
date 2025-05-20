package com.database.users

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("Users") {
    val login = varchar("login", 25)
    val password = varchar("password", 25)
    val username = varchar("username",30)
    val email = varchar("email", 25)

    fun insert(userDTO: UserDTO) {
        transaction {
            insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email
            }
        }
    }
    fun fetchUser(login: String): UserDTO? {
        return try {
            val result: UserDTO? = transaction {
                Users
                    .select { Users.login eq login }
                    .singleOrNull()
                    ?.let { row ->
                        UserDTO(
                            login = row[Users.login],
                            password = row[Users.password],
                            username = row[Users.username],
                            email = row[Users.email]
                        )
                    }
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}