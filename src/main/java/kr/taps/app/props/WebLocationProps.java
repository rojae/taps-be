package kr.taps.app.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebLocationProps {

  @Value("${tistory.apis.authorize.uri}")
  public String tistoryAuthorizeUri;

  @Value("${tistory.apis.authorize.state}")
  public String stateCode;

  @Value("${tistory.apis.accessToken.uri}")
  public String tistoryAccessTokenUri;

}
