package org.example.desensitization.user.account.repository;

import org.example.desensitization.user.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    UserAccount findByUsername(String username);

}