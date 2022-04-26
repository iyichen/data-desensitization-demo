package org.example.desensitization.user.data.repository;

import org.example.desensitization.user.data.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

}