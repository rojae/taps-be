package kr.taps.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoRequest {
  private String email;
  private String serviceType;
  private String clientId;
  private String clientSecret;
}
