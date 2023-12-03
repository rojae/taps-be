package kr.taps.app.api.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kr.taps.app.api.common.exception.ApiRuntimeException;
import kr.taps.app.base.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
@RequiredArgsConstructor
public class Aes256 {
    public static String alg = "AES/CBC/PKCS5Padding";

    @Value("${cipher.aesKey}")
    public String key;

    public String encrypt(String text) {
        Cipher cipher = null;
        String iv = key.substring(0, 16);

        try {
            cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            return Base64Utils.encodeToUrlSafeString(encrypted);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new ApiRuntimeException(ErrorCode.CIPHER_ENCRYPT_ERROR);
        }
    }

    public String decrypt(String cipherText){
        try{
            Cipher cipher = Cipher.getInstance(alg);
            String iv = key.substring(0, 16);

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64Utils.decodeFromUrlSafeString(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new ApiRuntimeException(ErrorCode.CIPHER_DECRYPT_ERROR);
        }
    }

}
