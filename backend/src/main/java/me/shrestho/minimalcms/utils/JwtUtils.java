package me.shrestho.minimalcms.utils;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.utils.enums.TokenType;

import java.sql.Date;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtUtils {

    private String jwtSecret = "SOME-VERY-SECRET-KEY-THAT-SHOULD-NOT-BE-HERE";

    private long jwtAccessExpirationMs = 10 * 60 * 1000; // 10 minutes
    private long jwtRefreshExpirationMs = 432000000; // 5days

    public String generateToken(User userData, TokenType subject) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        Long exp = System.currentTimeMillis()
                + (subject == TokenType.ACCESS ? jwtAccessExpirationMs : jwtRefreshExpirationMs);

        String token = JWT.create()
                .withIssuer("mcms")
                .withClaim("id", userData.getId().toString())
                .withClaim("name", userData.getName())
                .withClaim("username", userData.getUsername())
                .withClaim("email", userData.getEmail())
                .withClaim("created", userData.getCreated().toString())
                .withClaim("updated", userData.getUpdated().toString())
                .withClaim("role", userData.getRole().toString())
                .withExpiresAt(new Date(exp))
                .withSubject(subject.toString())
                .sign(algorithm);

        return token;
    }

    public DecodedJWT verifyTokenAndReturnDecoded(String refreshToken) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("mcms")
                .build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        return decodedJWT;

    }

}
