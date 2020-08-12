package com.ihrm.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseJwtTest {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ParseJwtTest.class);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OCIsInN1YiI6InpteSIsImlhdCI6MTU5NzA1NjcxMn0.Ycdbwg69uG4zAhi1Ff2nezlyZoURGrTT0xq3G4_FF5c";
        Claims claims = Jwts.parser().setSigningKey("ihrm").parseClaimsJws(token).getBody();
        logger.info("id= "+claims.getId());
        logger.info("时间为= "+claims.getIssuedAt());
        logger.info("内容为= "+claims.getSubject());
    }
}
