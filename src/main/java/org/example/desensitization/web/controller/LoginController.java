package org.example.desensitization.web.controller;

import org.example.desensitization.user.account.UserAccount;
import org.example.desensitization.user.account.repository.UserAccountRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private UserAccountRepository userAccountRepository;

    @RequestMapping("register")
    public UserAccount register(@RequestParam(value = "username", defaultValue = "admin") String username,
                                @RequestParam(value = "password", defaultValue = "abc1234") String password) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (userAccount != null) {
            throw new IllegalArgumentException("username exist");
        }
        userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        userAccountRepository.save(userAccount);
        return userAccount;
    }

    @RequestMapping("login")
    public UserAccount login(@RequestParam(value = "username", defaultValue = "admin") String username,
                             @RequestParam(value = "password", defaultValue = "abc1234") String password) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (userAccount == null || !BCrypt.checkpw(password, userAccount.getPassword())) {
            throw new IllegalArgumentException("username/password error");
        }
        return userAccount;
    }
}
