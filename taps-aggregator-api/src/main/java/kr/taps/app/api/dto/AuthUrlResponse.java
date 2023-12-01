package kr.taps.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUrlResponse {
  private String url;

  public static AuthUrlResponse from(String baseUrl, String clientId, String redirectUri, String state, String responseType){
    return new AuthUrlResponse(baseUrl + "?"
        + "client_id=" + clientId
        + "&redirect_uri=" + redirectUri
        + "&state=" + state
        + "&response_type=" + responseType
    );
  }

}
