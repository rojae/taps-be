package kr.taps.app.api.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UriProps {

  @Value("${taps.aggregator-api.uri} + ${taps.aggregator-api.authorize}")
  public String authorizeUrl;

}
