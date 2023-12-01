package kr.taps.app.api.controller;

import kr.taps.app.api.dto.ClientInfoRequest;
import kr.taps.app.api.dto.ClientInfoResponse;
import kr.taps.app.base.TapsApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

  @PostMapping("/info/client")
  public TapsApiResponse<ClientInfoResponse> clientInfo(@RequestBody ClientInfoRequest request){
    return new TapsApiResponse<>();
  }

}
