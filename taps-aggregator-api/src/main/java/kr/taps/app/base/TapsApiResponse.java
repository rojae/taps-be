package kr.taps.app.base;

import lombok.Data;

@Data
public class TapsApiResponse<T> {
  private String resultCode;
  private String message;
  private T data;

  public TapsApiResponse<T> ok(T t) {
    this.resultCode = ErrorCode.OK.getCode();
    this.setMessage("");
    this.data = t;
    return this;
  }

  public TapsApiResponse<T> fail(ErrorCode errorCode) {
    this.resultCode = errorCode.getCode();
    this.message = errorCode.getMessage();
    return this;
  }

  public TapsApiResponse<T> fail(ErrorCode errorCode, T t) {
    this.resultCode = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.data = t;
    return this;
  }

  public TapsApiResponse<T> setResponse(ErrorCode errorCode, T t) {
    this.resultCode = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.data = t;
    return this;
  }

  public TapsApiResponse<T> setResponse(ErrorCode errorCode) {
    this.resultCode = errorCode.getCode();
    this.message = errorCode.getMessage();
    return this;
  }

  public TapsApiResponse() {
    this.resultCode = ErrorCode.OK.getCode();
    this.message = ErrorCode.OK.getMessage();
  }

  public void setData(T t) {
    this.data = t;
  }
  
}
