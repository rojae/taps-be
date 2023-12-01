package kr.taps.app.api.common.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ServiceType {
  TISTORY("TISTORY", "티스토리"),
  UNKNOWN("UNKNOWN", "알수없음");

  private final String code;
  private final String desc;

  ServiceType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static ServiceType ofCode(String code){
    return Arrays.stream(ServiceType.values())
        .filter(e -> e.getCode().equalsIgnoreCase(code))
        .findAny()
        .orElse(ServiceType.UNKNOWN);
  }

}
