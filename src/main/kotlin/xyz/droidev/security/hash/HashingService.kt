package xyz.droidev.security.hash

interface HashingService {

    fun getHashedValue(value: String): String

    fun compareHashedValues(value: String, hashedValue: String): Boolean
}