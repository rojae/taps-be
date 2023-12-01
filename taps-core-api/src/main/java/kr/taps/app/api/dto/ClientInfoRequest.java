package kr.taps.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoRequest {
  private String clientId;
  private String clientSecret;

  // USER PK
  private String email;
}
