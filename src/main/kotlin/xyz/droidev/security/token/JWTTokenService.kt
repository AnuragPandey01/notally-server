package xyz.droidev.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JWTTokenService: TokenService {
    override fun generateToken(tokenConfig: TokenConfig, vararg claim: TokenClaim): String {
        var token = JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.validityInMs))

        claim.forEach {
            token = token.withClaim(it.name, it.value)
        }

        return token.sign(Algorithm.HMAC256(tokenConfig.secret))
    }
}