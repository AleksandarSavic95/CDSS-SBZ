package ftn.sbz.cdssserver.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TokenUtils {

    @Value("myXAuthSecret")
    private String secret;

    @Value("18000")
    private Long expiration;

    public String getUsernameFromToken(String authToken) {
        String username;
        try {
            Claims claims = this.getClaimsFromToken(authToken);
            username = Optional.ofNullable(claims.getSubject()).orElseThrow(() -> new Exception("Null"));
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String authToken) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("myXAuthSecret").parseClaimsJws(authToken).getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration2;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration2 = Optional.ofNullable(claims.getExpiration()).orElseThrow(() -> new Exception("null"));
        } catch (Exception e) {
            expiration2 = null;
        }
        return expiration2;
    }

    private boolean hasExpired(String token) {
        final Date expiration1 = this.getExpirationDateFromToken(token);
        return expiration1.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !hasExpired(token);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date(System.currentTimeMillis()));
        claims.put("roles", userDetails.getAuthorities());
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, "myXAuthSecret").compact();
    }

}
