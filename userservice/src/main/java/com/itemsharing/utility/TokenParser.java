package com.itemsharing.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by z00382545 on 9/19/17.
 */
public class TokenParser {

    public static String getUsername(String token, String signingKey) {
        String username = "";

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey.getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();

            username = (String) claims.get("user_name");

            return username;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return username;
    }
}
