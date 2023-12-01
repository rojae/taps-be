package kr.taps.app.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import kr.taps.app.api.dto.AuthRequest;
import kr.taps.app.api.dto.AuthUrlRequest;
import kr.taps.app.api.dto.AuthUrlResponse;
import kr.taps.app.api.dto.ClientInfoRequest;
import kr.taps.app.api.dto.ClientInfoResponse;
import kr.taps.app.api.props.TistoryProps;
import kr.taps.app.api.service.AuthService;
import kr.taps.app.base.TapsApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/gate")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final TistoryProps tistoryProps;
  private final AuthService authService;

  /**
   * 01. 서비스 이용을 위한 사이트들의 클라이이언트 아이디, 클라이언트 시크릿을 저장 (토큰 필요)
   */
  @PostMapping(value = "/authorize/client-info")
  public TapsApiResponse<ClientInfoResponse> clientInfo(@RequestBody ClientInfoRequest request){
    return authService.clientInfo(request.getClientId(), request.getClientSecret());
  }

  /**
   * 02. 서비스 허용을 하기 위한 API (실제 외부 서비스들의 코드 발급을 위함)
   */
  @PostMapping(value = "/authorize/{serviceType}")
  public TapsApiResponse<AuthUrlResponse> authUrl(@PathVariable String serviceType, @ModelAttribute AuthUrlRequest request) {
    return authService.authUrl(serviceType, request.getClientId());
  }

  /**
   * 03. 토큰 발급 이후에 저장 과정
   * SetviceType에 따른 code가 넘어왔기 때문에, AccessToken을 발급처리 하고 디비 저장
   */
  // Authorize Code가 넘어왔으므로, AccessToken을 발급받자
  @GetMapping(value = "/authorize/token")
  public String authorize(@ModelAttribute AuthRequest request){
    if(!request.getState().equals(tistoryProps.stateCode)){
      return "error";
    }
    else{
      // AccessToken 발급
      log.info("code : {}, state : {}", request.getCode(), request.getState());
      String accessToken = getAccessToken(
          "...",
          "...",
          "http://127.0.0.1:8080/gate/authorize",
          request.getCode()
      );
      log.info("accessToken : {}", accessToken);
      return "authorize";
    }
  }

  /**
   * Tistory AccessToken을 수신하는 메소드
   * @param clientId
   * @param clientSecret
   * @param redirectUri
   * @param code
   * @return
   */
  public String getAccessToken(String clientId, String clientSecret, String redirectUri, String code) {
    try {
      String url = tistoryProps.tistoryAccessTokenUri + "?"
          + "client_id=" + clientId + "&"
          + "client_secret=" + clientSecret + "&"
          + "redirect_uri=" + redirectUri + "&"
          + "code=" + code + "&"
          + "grant_type=authorization_code";

      URL obj = new URL(url);

      HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

      String line = br.readLine();
      if(line.contains("access_token=")){
        return line.split("=")[1];
      }
      else{
        return "";
      }
    } catch (IOException e) {
      e.printStackTrace();
      return "Error";
    }
  }

}
