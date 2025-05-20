package com.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

// Определение таблицы токенов
object Tokens : Table("tokens") {
    private val login = Tokens.varchar("login", 25)
    private val id = Tokens.varchar("id", 50)
    private val token = Tokens.varchar("token", 50)

    // Функция для вставки токена
    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[login] = tokenDTO.login
                it[id] = tokenDTO.rowId
                it[token] = tokenDTO.token
            }
        }
    }
}