package org.jnyou.anoteinventoryservice.tools.wechat;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

/**
 */

/**
 * @author jnyou
 * @description:
 * AES-128-CBC 加密方式
 * AES-128-CBC可以自己定义“密钥”和“偏移量“。
 * AES-128是jdk自动生成的“密钥”。
 */
@Slf4j
public class AesCbcUtil {

    static {
        //BouncyCastle是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES解密
     *
     * @param data           //密文，被加密的数据
     * @param key            //秘钥
     * @param iv             //偏移量
     * @param encodingFormat //解密后的结果需要进行的编码
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv, String encodingFormat) {
        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(data);
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv);

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("NoSuchAlgorithmException: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            log.error("NoSuchPaddingException: " + e.getMessage());
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
            log.error("InvalidParameterSpecException: " + e.getMessage());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            log.error("InvalidKeyException: " + e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            log.error("NoSuchAlgorithmException: " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            log.error("IllegalBlockSizeException: " + e.getMessage());
        } catch (BadPaddingException e) {
            e.printStackTrace();
            log.error("BadPaddingException: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("UnsupportedEncodingException: " + e.getMessage());
        }
        return null;
    }

}