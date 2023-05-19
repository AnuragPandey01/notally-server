package xyz.droidev.security.token

interface TokenService {

    fun generateToken(
        tokenConfig: TokenConfig,
        vararg claim: TokenClaim
    ): String
}