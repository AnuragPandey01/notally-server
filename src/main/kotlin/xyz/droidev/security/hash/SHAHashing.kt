package xyz.droidev.security.hash

import org.apache.commons.codec.digest.DigestUtils

object SHAHashing : HashingService {
    override fun getHashedValue(value: String): String {
        return DigestUtils.sha256Hex(value)
    }

    override fun compareHashedValues(value: String, hashedValue: String): Boolean {
        return getHashedValue(value) == hashedValue
    }
}