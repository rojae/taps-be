package kr.taps.app.api.dto;

import lombok.Data;

@Data
public class ClientInfoRequest {
  private String serviceType;
  private String clientId;
  private String clientSecret;
}
