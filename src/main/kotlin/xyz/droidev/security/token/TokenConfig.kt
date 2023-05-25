package xyz.droidev.security.token

data class TokenConfig(
    val issuer: String,
    val validityInMs: Long,
    val secret: String
)
