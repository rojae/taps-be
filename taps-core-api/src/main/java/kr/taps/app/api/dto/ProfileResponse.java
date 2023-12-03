package kr.taps.app.api.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.taps.app.api.common.utils.DateTimeUtils;
import kr.taps.app.api.entity.TblUserCredential;
import kr.taps.app.api.entity.TblUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
  private Long userNo;
  private String userEmail;
  private List<UserCredential> userCredentials;


  public static ProfileResponse from(TblUserInfo userInfo, List<TblUserCredential> userCredentials){
    return new ProfileResponse(userInfo.getUserNo(), userInfo.getUserEmail(), UserCredential.from(userCredentials));
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserCredential{
    private String clientId;
    private String clientSecret;
    private String serviceType;
    private String createdAt;

    public static List<UserCredential> from(List<TblUserCredential> userCredentials){
      if (userCredentials.isEmpty()) {
        return Collections.emptyList();
      }
      else{
        return userCredentials.stream()
            .map(i -> new UserCredential(i.getClientId(), i.getClientSecret(), i.getServiceType().getCode(), DateTimeUtils.toyyyyMMddHHmmss(i.getInsDate())))
            .collect(Collectors.toList());
      }
    }
  }

}

