package kr.taps.app.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import kr.taps.app.api.common.exception.ApiRuntimeException;
import kr.taps.app.base.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthToken {
    @Getter
    private final String token;
    private final Key key;
    
    private static final String AUTHORITIES_KEY = "role";
    
    public JwtAuthToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }
    
    JwtAuthToken(String id, String role, Map<String, Object> claims, Date expiredDate, Key key) {
        this.key = key;
        this.token = createJwtAuthToken(id, role, claims, expiredDate).get();
    }
    
    public boolean validate() {
        return getData() != null;
    }
    
    public Claims getData() {
        
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            log.error ("Invalid JWT signature.");
            throw new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
            throw new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
            throw new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
            throw new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED);
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.");
            throw new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED);
        }
    }
    
    private Optional<String> createJwtAuthToken(String id, String role, Map<String, Object> claims, Date expiredDate) {
        
        String token = Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact();
        
        return Optional.ofNullable(token);
    }

    public LocalDateTime getExpireDate(){
        Date date = this.getData().getExpiration();

        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

}