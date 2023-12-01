package kr.taps.app.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TapsCoreApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(TapsCoreApiApplication.class, args);
  }

}
