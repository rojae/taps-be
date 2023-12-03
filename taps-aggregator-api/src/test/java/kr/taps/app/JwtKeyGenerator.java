package kr.taps.app;

import java.security.SecureRandom;
import org.junit.jupiter.api.Test;

public class JwtKeyGenerator {

  private static int jwtKeySize = 38;

  @Test
  public void jwtKeyGenerate(){
    System.out.println(this.generateRandomString(jwtKeySize));
  }

  public String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder randomString = new StringBuilder(length);
    SecureRandom secureRandom = new SecureRandom();

    for (int i = 0; i < length; i++) {
      int randomIndex = secureRandom.nextInt(characters.length());
      randomString.append(characters.charAt(randomIndex));
    }

    return randomString.toString();
  }


}
