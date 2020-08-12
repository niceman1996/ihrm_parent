package com.ihrm.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CreateJwtTest {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(CreateJwtTest.class);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("88").setSubject("zmy")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "ihrm");
        String token = jwtBuilder.compact();
        logger.info(token);
    }
}
