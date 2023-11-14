package kr.taps.app.controller;

import kr.taps.app.dto.LoginUserRequest;
import kr.taps.app.dto.LoginUserResponse;
import kr.taps.app.dto.LoginUserResponse.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @PostMapping("/api/login")
  public LoginUserResponse loginUser(@RequestBody LoginUserRequest request){
    if(request.getEmail().equals("test@kakao.com") && request.getPassword().equals("password")){
      return new LoginUserResponse("ACCESS_TOKEN", "OK", new UserInfo("jaeseoh", "19960131"));
    }
    else{
      return new LoginUserResponse("", "FAILED", new UserInfo("", ""));
    }
  }

}
