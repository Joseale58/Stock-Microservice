package com.emazon.stock_service.infraestructure.output.security.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {

    //Clave secreta para la creación del token, que es extraida del .env
    @Value("${JWT_SECRET}")
    private String privateKey;

    //Para crear el token
    public String createToken(Authentication authentication) {
        //Algoritmo a utilizar en la creación del token
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();


        //Creación del token
        String jwt = JWT.create() //Crea un token
                //Los claims son los datos del payload del token
                .withSubject(username) //Añade el nombre de usuario
                .withClaim("role", role) //Añade el rol del usuario
                .withIssuedAt(new Date(System.currentTimeMillis())) //Añade la fecha de creación del token
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) //Añade la fecha de expiración del token
                .withJWTId(UUID.randomUUID().toString()) //Añade un identificador único y random al token
                .sign(algorithm);

        return jwt;
    }

    //Para validar el token
    public DecodedJWT validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token); //Si el token es invalido, se lanza una excepción
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalido, no autorizado");
        }
    }

    //Para extraer el nombre de usuario del token
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    //Para devolver un claim específico
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claim) {
        return decodedJWT.getClaim(claim);
    }

    //Para devolver todos los claims
    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
