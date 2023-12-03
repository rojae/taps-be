package kr.taps.app.api.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProps {

  @Value("${jwt.header}")
  public String headerName;
  @Value("${jwt.secret}")
  public String secret;
  @Value("${jwt.securePath}")
  public String[] securePath;

}
