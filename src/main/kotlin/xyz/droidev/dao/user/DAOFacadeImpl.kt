package xyz.droidev.dao.user

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import xyz.droidev.dao.DatabaseFactory.dbQuery
import xyz.droidev.model.User
import xyz.droidev.model.Users
import java.time.LocalDateTime
import java.util.*

class DAOFacadeImpl : DAOFacade {

    override suspend fun addUser(name: String, email: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToUser(it)}
    }

    override suspend fun editUser(id: String, name: String, email: String, password: String): User? = dbQuery {
        val updateStatement = Users.update({ Users.id eq UUID.fromString(id) }) {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.password] = password
        } > 0

        if (updateStatement) getUser(id) else null
    }

    override suspend fun deleteUser(id: String): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq UUID.fromString(id) } > 0
    }

    override suspend fun getUser(id: String): User? = dbQuery {
        Users.select { Users.id eq UUID.fromString(id) }.map{ resultRowToUser(it) }.singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }.map{ resultRowToUser(it) }.singleOrNull()
    }

    override suspend fun getAllUsers(): List<User> = dbQuery{
        Users.selectAll().map { resultRowToUser(it) }
    }

    override suspend fun updateLastSignedIn(id: String): Boolean = dbQuery {

       Users.update({ Users.id eq UUID.fromString(id) }) {
            it[Users.lastSignedIn] = LocalDateTime.now()
        } > 0
    }

    override suspend fun updateLastActive(id: String): Boolean = dbQuery {
        Users.update({ Users.id eq UUID.fromString(id) }) {
            it[Users.lastActive] = LocalDateTime.now()
        } > 0
    }

    private fun resultRowToUser(it : ResultRow) = User(
        id = it[Users.id].toString(),
        name = it[Users.name],
        email = it[Users.email],
        password = it[Users.password],
        createdAt = it[Users.createdAt],
        lastSignedIn = it[Users.lastSignedIn],
        lastActive = it[Users.lastActive]
    )
}

val userDao: DAOFacade = DAOFacadeImpl()