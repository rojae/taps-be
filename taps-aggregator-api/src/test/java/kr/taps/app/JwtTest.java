package kr.taps.app;

import java.util.Map;
import kr.taps.app.api.TapsAggregatorApiApplication;
import kr.taps.app.api.jwt.JwtClaimsDto;
import kr.taps.app.api.jwt.TokenRole;
import kr.taps.app.api.security.Aes256;
import kr.taps.app.api.security.JwtAuthTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TapsAggregatorApiApplication.class)
public class JwtTest {

  private Long userNo = 1L;
  private String userEmail = "email@google.com";
  private String salt = "SALT01";

  @Autowired
  private JwtAuthTokenProvider jwtAuthTokenProvider;

  @Autowired
  private Aes256 aes256;

  @Test
  public void jwt(){
    Map<String, Object> claims = new JwtClaimsDto(userNo, userEmail, salt).toMap();

    String jwt = jwtAuthTokenProvider.createAuthToken(String.valueOf(userNo), TokenRole.USER.getRole(), claims).getToken();
    System.out.println("JWT : " + jwt);

    String encryptedJwt = aes256.encrypt(jwt);
    System.out.println("Encrypted JWT : " + encryptedJwt);
  }

}
