package kr.taps.app.base;

import java.util.Arrays;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
  // --- 0000 OK ---
  OK("0000", "OK", HttpStatus.OK),

  // 14xx Request Error
  BAD_REQUEST("1401", "잘못된 요청입니다"),
  INVALID_PARAMETER("1402", "입력값이 올바르지 않습니다."),
  DATA_NOT_FOUND("1414", "데이터가 존재하지 않습니다."),
  REQUEST_NOT_FOUND("1415", "인증 요청 데이터가 존재하지 않습니다."),
  ALREADY_SAME_DATA("1416", "요청 데이터가 기존 데이터와 동일한 데이터입니다"),

  // SYSTEM ERROR
  UNDESCRIBED("9999", "일시적인 시스템 지연이 발생했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);

  ////////////////////////////////////////////////////////////////////////////////////

  private final String code;
  private final String message;
  private final HttpStatus status;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
    this.status = HttpStatus.OK;
  }

  ErrorCode(String code, String message, HttpStatus status) {
    this.code = code;
    this.message = message;
    this.status = status;
  }

  public String getCode() {
    return this.code;
  }

  public HttpStatus getStatus() {
    return this.status;
  }

  public int getStatusCode() {
    return this.status.value();
  }

  public String getMessage() {
    return this.message;
  }

  public static ErrorCode ofCode(String code){
    return Arrays.stream(ErrorCode.values())
        .filter(e -> e.getCode().equalsIgnoreCase(code))
        .findAny()
        .orElse(ErrorCode.UNDESCRIBED);
  }
}