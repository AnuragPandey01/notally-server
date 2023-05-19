package xyz.droidev.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.droidev.model.Notes
import xyz.droidev.model.Users

object DatabaseFactory{

    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcUrl = "jdbc:postgresql://localhost:5432/notally"
        val database = Database.connect(jdbcUrl, driverClassName,user="notally",password="Notally@1304")

        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.createMissingTablesAndColumns(Users)
            SchemaUtils.create(Notes)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}