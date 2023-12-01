package kr.taps.app.api.common.exception;

import kr.taps.app.base.ErrorCode;
import kr.taps.app.base.TapsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
//  private static final Logger moALogger = LoggerFactory.getLogger("MoALogger");

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<TapsApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

    log.error(e.getMessage(), e);

    TapsApiResponse response = new TapsApiResponse();
    response.setResultCode(ErrorCode.BAD_REQUEST.getCode());
    response.setMessage(ExceptionUtils.getMessage(e));

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(ApiRuntimeException.class)
  protected ResponseEntity<TapsApiResponse> handleApiRuntimeException(ApiRuntimeException e) {

    log.error(e.getMessage(), e);

    TapsApiResponse response = new TapsApiResponse();
    response.setResultCode(e.getCode());
    response.setMessage(e.getMessage());

    return new ResponseEntity<>(response, HttpStatus.valueOf(e.getStatus()));
  }

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<TapsApiResponse> handleRuntimeException(RuntimeException e) {

    log.error(e.getMessage(), e);

    TapsApiResponse<String> response = new TapsApiResponse<>();
    response.setResultCode(ErrorCode.UNDESCRIBED.getCode());
    response.setMessage(ExceptionUtils.getMessage(e));

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}