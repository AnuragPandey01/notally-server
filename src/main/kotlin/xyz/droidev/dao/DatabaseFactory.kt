package xyz.droidev.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.droidev.model.Notes
import xyz.droidev.model.Users

object DatabaseFactory{

    fun init(jdbcUrl : String,driverClassName: String) {
        Database.connect(createHikariDataSource(url = jdbcUrl,driver= driverClassName))
        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.createMissingTablesAndColumns(Users)
            SchemaUtils.create(Notes)
        }
    }

    private fun createHikariDataSource(
        url: String,
        driver: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}