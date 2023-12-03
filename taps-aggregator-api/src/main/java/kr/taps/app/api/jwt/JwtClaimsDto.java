package kr.taps.app.api.jwt;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
public class JwtClaimsDto {

    private Long userNo;            // 고객번호
    private String userEmail;       // 고객이메일
    private String salt;            // SALT

    @Builder
    public JwtClaimsDto(Long userNo, String userEmail, String salt) {
        this.userNo = userNo;
        this.userEmail = userEmail;
        this.salt = salt;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", this.getUserNo());
        map.put("userEmail", this.getUserEmail());
        map.put("salt", this.getSalt());

        return map;
    }

    public Map<String, Object> toMap(JwtClaimsDto claims){
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", claims.getUserNo());
        map.put("userEmail", claims.getUserEmail());
        map.put("salt", claims.getSalt());

        return map;
    }


}