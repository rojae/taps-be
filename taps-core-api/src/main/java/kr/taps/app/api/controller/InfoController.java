package kr.taps.app.api.controller;

import kr.taps.app.api.dto.ClientInfoRequest;
import kr.taps.app.api.dto.ClientInfoResponse;
import kr.taps.app.api.service.UserService;
import kr.taps.app.base.ErrorCode;
import kr.taps.app.base.TapsApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InfoController {

  private final UserService userService;

  @PostMapping("/info/client")
  public TapsApiResponse<ClientInfoResponse> clientInfo(@RequestBody ClientInfoRequest request){
    if(userService.save(request) > 1L)
      return new TapsApiResponse<ClientInfoResponse>().ok(null);
    else
      return new TapsApiResponse<ClientInfoResponse>().fail(ErrorCode.UNDESCRIBED);
  }

}
