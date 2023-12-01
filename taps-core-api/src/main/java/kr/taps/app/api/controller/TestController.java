package kr.taps.app.api.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  public String hello(){
    return "hello World";
  }

}
