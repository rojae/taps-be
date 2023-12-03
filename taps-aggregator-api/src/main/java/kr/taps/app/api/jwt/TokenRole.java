package kr.taps.app.api.jwt;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenRole {
  USER("USER"),
  UNKNOWN("UNKNOWN");

  private final String role;

  public static TokenRole ofCode(String code){
    return Arrays.stream(TokenRole.values())
        .filter(e -> e.getRole().equals(code))
        .findAny()
        .orElse(TokenRole.UNKNOWN);
  }

}
