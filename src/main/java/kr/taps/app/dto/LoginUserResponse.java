package kr.taps.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {
  private String accessToken;
  private String message;
  private UserInfo user;

  @Data
  @AllArgsConstructor
  public static class UserInfo{
    private String name;
    private String birthDate;
  }

}


