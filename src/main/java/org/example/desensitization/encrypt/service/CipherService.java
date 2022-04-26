package org.example.desensitization.encrypt.service;

import org.apache.commons.lang3.StringUtils;
import org.example.desensitization.encrypt.CipherData;
import org.example.desensitization.encrypt.repository.CipherDataRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CipherService {

    //密钥长度
    private static final int AES_KEY_SIZE = 256;

    //初始化向量长度
    private static final int GCM_IV_LENGTH = 12;

    //GCM身份认证Tag长度
    private static final int GCM_TAG_LENGTH = 16;

    @Resource
    private CipherDataRepository cipherDataRepository;

    public CipherResult encrypt(String data, String aad) throws Exception {
        // 生成密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE);
        SecretKey key = keyGenerator.generateKey();

        // 生成初始向量
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // 保存密钥&向量
        CipherData cipherData = new CipherData();
        cipherData.setIv(Base64.getEncoder().encodeToString(iv));
        cipherData.setSecretKey(Base64.getEncoder().encodeToString(key.getEncoded()));
        cipherDataRepository.save(cipherData);

        //加密
        byte[] aadBytes = StringUtils.isBlank(aad) ? null : aad.getBytes();
        CipherResult cipherResult = new CipherResult();
        cipherResult.setCipherText(Base64.getEncoder().encodeToString(encrypt(data.getBytes(), key, iv, aadBytes)));
        cipherResult.setCipherId(cipherData.getId());

        return cipherResult;
    }

    public String decrypt(int cipherId, String cipherText, String aad) throws Exception {
        CipherData cipherData = cipherDataRepository.findById(cipherId)
                .orElseThrow(() -> new IllegalArgumentException("invalid cipherId"));
        // 获取密钥
        byte[] decodedKey = Base64.getDecoder().decode(cipherData.getSecretKey());
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        // 获取初始向量
        byte[] decodedIv = Base64.getDecoder().decode(cipherData.getIv());

        byte[] aadBytes = StringUtils.isBlank(aad) ? null : aad.getBytes();
        return new String(decrypt(Base64.getDecoder().decode(cipherText.getBytes()), originalKey, decodedIv, aadBytes));
    }

    private byte[] encrypt(byte[] plaintext, SecretKey key, byte[] iv, byte[] aad) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getEncoded(), "AES"), new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv));

        if (aad != null) {
            cipher.updateAAD(aad);
        }
        return cipher.doFinal(plaintext);
    }

    private byte[] decrypt(byte[] cipherText, SecretKey key, byte[] iv, byte[] aad) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getEncoded(), "AES"), new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv));

        if (aad != null) {
            cipher.updateAAD(aad);
        }
        return cipher.doFinal(cipherText);
    }

}
