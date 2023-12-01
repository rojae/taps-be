package kr.taps.app.api.dto;

import lombok.Data;

@Data
public class AuthUrlRequest {
  private String clientId;
  private String redirectUri;
}
