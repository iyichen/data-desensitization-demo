package org.example.desensitization.web.controller;

import org.example.desensitization.encrypt.service.CipherResult;
import org.example.desensitization.encrypt.service.CipherService;
import org.example.desensitization.user.data.UserData;
import org.example.desensitization.user.data.repository.UserDataRepository;
import org.example.desensitization.util.DesensitizationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/data/")
public class UserDataController {

    private final int userId = 10;

    @Resource
    private CipherService cipherService;
    @Resource
    private UserDataRepository userDataRepository;

    @RequestMapping("save")
    public UserData right(@RequestParam(value = "name", defaultValue = "吴彦祖") String name,
                          @RequestParam(value = "idCard", defaultValue = "430722199101131921") String idCard,
                          @RequestParam(value = "aad", defaultValue = "ed45s") String aad) throws Exception {
        UserData userData = new UserData();
        userData.setId(userId);
        // 保存脱敏数据
        userData.setName(DesensitizationUtils.desensitize(name, DesensitizationUtils.DesensitizationType.NAME));
        userData.setIdCard(DesensitizationUtils.desensitize(idCard, DesensitizationUtils.DesensitizationType.ID_CARD));

        // 加密姓名
        CipherResult cipherResultName = cipherService.encrypt(name, aad);
        userData.setNameCipherId(cipherResultName.getCipherId());
        userData.setNameCipherText(cipherResultName.getCipherText());

        // 加密身份证
        CipherResult cipherResultIdCard = cipherService.encrypt(idCard, aad);
        userData.setIdCardCipherId(cipherResultIdCard.getCipherId());
        userData.setIdCardCipherText(cipherResultIdCard.getCipherText());

        return userDataRepository.save(userData);
    }

    @RequestMapping("get-real-data")
    public Map<String, String> get(@RequestParam(value = "aad", defaultValue = "ed45s") String aad) throws Exception {
        Map<String, String> result = new HashMap<>();

        UserData userData = userDataRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId[" + userId + "] not exist"));
        // 解密姓名
        String name = cipherService.decrypt(userData.getNameCipherId(), userData.getNameCipherText(), aad);
        // 解密身份证
        String idCard = cipherService.decrypt(userData.getIdCardCipherId(), userData.getIdCardCipherText(), aad);
        result.put("name", name);
        result.put("idCard", idCard);
        return result;
    }

}
