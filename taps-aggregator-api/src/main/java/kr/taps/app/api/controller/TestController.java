package kr.taps.app.api.controller;

import kr.taps.app.api.jwt.JwtTokenValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

  @PostMapping("/api")
  @JwtTokenValidation(headerName = "Authorization")
  public String test(@RequestHeader(value = "Authorization") String authorization){
    log.info("Authorization : {}", authorization);
    return "hello world";
  }


}
