package kr.taps.app.api.common.exception;

import kr.taps.app.base.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ApiRuntimeException extends RuntimeException {
  private String code;
  private String message;
  private int status;

  public ApiRuntimeException() {
    super();
    this.code = ErrorCode.UNDESCRIBED.getCode();
    this.status = ErrorCode.UNDESCRIBED.getStatus().value();
  }

  private ApiRuntimeException(String message) {
    super(message);
    this.message = message;
    this.code = ErrorCode.UNDESCRIBED.getCode();
    this.status = ErrorCode.UNDESCRIBED.getStatus().value();
  }

  public ApiRuntimeException(ErrorCode errorCode) {
    this(errorCode.getMessage());
    this.code = errorCode.getCode();
    this.status = errorCode.getStatus().value();
  }

  public ApiRuntimeException(ErrorCode errorCode, String message) {
    this(message);
    this.code = errorCode.getCode();
    this.status = errorCode.getStatus().value();
  }
}