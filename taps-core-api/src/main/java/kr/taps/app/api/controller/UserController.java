package kr.taps.app.api.controller;

import kr.taps.app.api.dto.ProfileResponse;
import kr.taps.app.api.dto.SignupRequest;
import kr.taps.app.api.dto.SignupResponse;
import kr.taps.app.api.service.UserService;
import kr.taps.app.base.ErrorCode;
import kr.taps.app.base.TapsApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public TapsApiResponse<SignupResponse> signup(@RequestBody SignupRequest request){
    return new TapsApiResponse<SignupResponse>().ok(userService.save(request));
  }

  @GetMapping("/profile")
  public TapsApiResponse<ProfileResponse> profile(@RequestParam(value = "userNo") Long userNo){
      return userService.profile(userNo);
  }


}
