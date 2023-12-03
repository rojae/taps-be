package kr.taps.app.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import kr.taps.app.api.common.exception.ApiRuntimeException;
import kr.taps.app.api.jwt.TokenRole;
import kr.taps.app.base.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthTokenProvider {
    private final Key key;
    private static final long TOKEN_RETENTION_MINUTES = 30;

    public JwtAuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public JwtAuthToken createAuthToken(String id, String role, Map<String, Object> claims) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(TOKEN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant());
        return new JwtAuthToken(id, role, claims, expiredDate, key);
    }
    
    public JwtAuthToken convertAuthToken(String token) {
        return new JwtAuthToken(token, key);
    }

    public JwtAuthToken updateData(String token, Long userNo, String userEmail) {
        // 토큰 데이터 검증
        if(!this.convertAuthToken(token).validate()){
            throw new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED);
        }
        // 고객번호 검증 (고객번호는 항상 동일함)
        else if(!this.getUserNo(token).equals(-1L)){
            throw new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED);
        }

        JwtAuthToken jwtAuthToken = this.convertAuthToken(token);
        Claims data = jwtAuthToken.getData();

        Map<String, Object> claims = new HashMap<>();

        // 토큰 기존 정보 복제, 및 업데이트
        claims.put("userNo", userNo);
        claims.put("userEmail", userEmail);
        claims.put("salt", (String) data.get("salt"));

        return createAuthToken((String) data.get("userNo"), TokenRole.USER.getRole(), claims);
    }

    ////////////////////// Getter //////////////////////

    public Long getUserNo(String token){
        JwtAuthToken jwtAuthToken = this.convertAuthToken(token);
        Claims data = jwtAuthToken.getData();

        long userNo = -1L;

        if(data.get("userNo") != null){
            userNo = Long.parseLong(String.valueOf(data.get("userNo")));
        }

        return userNo;
    }

    public String getUserEmail(String token){
        JwtAuthToken jwtAuthToken = this.convertAuthToken(token);
        Claims data = jwtAuthToken.getData();
        return (String) data.get("userEmail");
    }

    public String getSalt(String token){
        JwtAuthToken jwtAuthToken = this.convertAuthToken(token);
        Claims data = jwtAuthToken.getData();
        return (String) data.get("salt");
    }


}