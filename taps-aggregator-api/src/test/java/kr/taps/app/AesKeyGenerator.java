package kr.taps.app;

import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;

public class AesKeyGenerator {

  @Test
  public void aes256KeyGenerate() {
    SecretKey aesKey = generateAes256Key();

    if (aesKey != null) {
      // 생성된 키 출력
      byte[] keyBytes = aesKey.getEncoded();
      System.out.println("Generated AES Key: " + bytesToHex(keyBytes));
    }
  }

  public SecretKey generateAes256Key() {
    try {
      // AES 키 생성기 생성
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");

      // 키 크기 설정 (256 비트)
      keyGen.init(256);

      // AES 키 생성
      SecretKey secretKey = keyGen.generateKey();

      return secretKey;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  // 바이트 배열을 16진수 문자열로 변환하는 헬퍼 메서드
  private String bytesToHex(byte[] bytes) {
    StringBuilder result = new StringBuilder();
    for (byte b : bytes) {
      result.append(String.format("%02X", b));
    }
    return result.toString();
  }

}