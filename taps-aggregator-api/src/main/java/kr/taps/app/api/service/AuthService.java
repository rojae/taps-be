package kr.taps.app.api.service;

import kr.taps.app.api.common.enums.ServiceType;
import kr.taps.app.api.common.exception.ApiRuntimeException;
import kr.taps.app.api.dto.AuthUrlResponse;
import kr.taps.app.api.dto.ClientInfoRequest;
import kr.taps.app.api.dto.ClientInfoResponse;
import kr.taps.app.api.props.TistoryProps;
import kr.taps.app.api.props.UriProps;
import kr.taps.app.base.ErrorCode;
import kr.taps.app.base.TapsApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final TistoryProps tistoryProps;
  private final UriProps uriProps;

  public Mono<TapsApiResponse<ClientInfoResponse>> clientInfo(String clientId, String clientSecret){
    // DB SAVE
    return Mono.just(new TapsApiResponse<ClientInfoResponse>().ok(new ClientInfoResponse("clientId")));
  }

  public TapsApiResponse<AuthUrlResponse> authUrl(String serviceType, String clientId){
    if( ServiceType.ofCode(serviceType) == ServiceType.TISTORY){
      return new TapsApiResponse<AuthUrlResponse>().ok(AuthUrlResponse.from(tistoryProps.tistoryAuthorizeUri, clientId, uriProps.authorizeUrl , tistoryProps.stateCode, "code"));
    }
    else{
      throw new ApiRuntimeException(ErrorCode.INVALID_PARAMETER);
    }
  }

}
