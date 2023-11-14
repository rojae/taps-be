package kr.taps.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import kr.taps.app.props.WebLocationProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gate")
@Slf4j
public class GateController {

  private final WebLocationProps webLocationProps;

  @GetMapping(value = "{serviceType}")
  public String getAuthenticationUrl(@PathVariable String serviceType, @RequestParam String clientId, @RequestParam String redirectUri) {
    if(serviceType.equals("tistory")){
      return "redirect:" + webLocationProps.tistoryAuthorizeUri + "?"
          + "client_id=" + clientId
          + "&redirect_uri=" + redirectUri
          + "&state=" + webLocationProps.stateCode
          + "&response_type=code";
    }
    else{
      return "error";
    }
  }

  // Authorize Code가 넘어왔으므로, AccessToken을 발급받자
  @GetMapping(value = "/authorize")
  public String authorize(@RequestParam String code, @RequestParam String state){
    if(!state.equals(webLocationProps.stateCode)){
      return "error";
    }
    else{
      // AccessToken 발급
      log.info("code : {}, state : {}", code, state);
      String accessToken = getAccessToken(
          "...",
          "...",
          "http://127.0.0.1:8080/gate/authorize",
          code
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
      String url = webLocationProps.tistoryAccessTokenUri + "?"
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
