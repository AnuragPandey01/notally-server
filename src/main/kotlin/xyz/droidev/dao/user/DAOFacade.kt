package xyz.droidev.dao.user

import xyz.droidev.model.User

interface DAOFacade {
    suspend fun addUser(name: String, email: String, password: String): User?
    suspend fun editUser(id: String, name: String, email: String, password: String): User?
    suspend fun deleteUser(id: String): Boolean
    suspend fun getUser(id: String): User?
    suspend fun getUserByEmail(email:String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun updateLastSignedIn(id: String): Boolean
    suspend fun updateLastActive(id: String): Boolean
}