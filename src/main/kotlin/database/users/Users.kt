package com.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("Users") {
    val phone = varchar("phone", 45)
    val name = varchar("name",45)
    val surname = varchar("surname",45)
    val email = varchar("email", 45)
    val password = varchar("password", 45)
    val status = varchar("status", 45)

    fun insert(userDTO: UserDTO) {
        transaction {
            insert {
                it[phone] = userDTO.phone
                it[name] = userDTO.name
                it[surname] = userDTO.surname
                it[email] = userDTO.email
                it[password] = userDTO.password
                it[status] = userDTO.status
            }
        }
    }
    fun fetchUser(login: String): UserDTO? {
        return try {
            val result: UserDTO? = transaction {
                Users
                    .select { email eq login }
                    .singleOrNull()
                    ?.let { row ->
                        UserDTO(
                            phone = row[phone],
                            name = row[name],
                            surname = row[surname],
                            email = row[email],
                            password = row[password],
                            status = row[status]
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