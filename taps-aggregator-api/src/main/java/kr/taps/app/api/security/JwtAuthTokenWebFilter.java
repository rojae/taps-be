package kr.taps.app.api.security;

import java.util.Arrays;
import kr.taps.app.api.common.exception.ApiRuntimeException;
import kr.taps.app.api.jwt.JwtProps;
import kr.taps.app.api.jwt.JwtTokenValidation;
import kr.taps.app.base.ErrorCode;
import kr.taps.app.base.TapsApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthTokenWebFilter implements WebFilter {

  private final JwtAuthTokenProvider jwtAuthTokenProvider;
  private final Aes256 aes256;
  private final JwtProps jwtProps;


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String requestPath = exchange.getRequest().getPath().toString();
    log.info("Request Path : {}", requestPath);

    // 요청된 경로가, 기설정한 예외경로가 아니라면 JWT 체크
    if(StringUtils.isNotEmpty(requestPath) && Arrays.asList(jwtProps.securePath).contains(requestPath)){
      return validateToken(exchange, jwtProps.headerName).flatMap(result -> {
            if (result) {
              // 토큰이 유효한 경우 계속 진행
              return chain.filter(exchange);
            }
            else {
              // 토큰이 유효하지 않은 경우 에러 응답 반환
              exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
              return exchange.getResponse().writeWith(Mono.empty());
            }
          });
    }
    else{
      // 어노테이션이 없으면 다음 필터로 계속 진행
      return chain.filter(exchange);
    }
  }

  private Mono<Boolean> validateToken(ServerWebExchange exchange, String headerName) {
    String encryptedToken = exchange.getRequest().getHeaders().getFirst(headerName);
    String token = "";
    JwtAuthToken jwtAuthToken = null;

    // JWT 토큰이 없는 경우
    if (StringUtils.isEmpty(encryptedToken)) {
      log.error("[JwtAuthTokenFilter] -- Can't Found Token In Request Header");
      return Mono.error(new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED, "토큰이 존재하지 않습니다"));
    }

    // JWT 토큰 복호화에 실파한 경우
    try {
      token = aes256.decrypt(encryptedToken);
      jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token);
    }
    catch (Exception e){
      log.error("[JwtAuthTokenFilter] -- Failed Decrypt Token | token : {}", encryptedToken);
      return Mono.error(new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED, "토큰 복호화에 실패했습니다"));
    }

    // JWT 토큰 인증에 실패한 경우 (만료 등의 사유)
    if (!jwtAuthToken.validate()) {
      log.error("[JwtAuthTokenFilter] -- Failed Validate Token | token : {}", token);
      return Mono.error(new ApiRuntimeException(ErrorCode.TOKEN_AUTHENTICATION_FAILED, "토큰 인증에 실패했습니다"));
    }
    // 성공함
    else {
      return Mono.just(true);
    }
  }

  private Mono<Void> handleTokenValidationFailure(Throwable error, ServerWebExchange exchange) {
    // 클라이언트에게 에러 응답을 보냄
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().writeWith(Mono.empty());
  }

}