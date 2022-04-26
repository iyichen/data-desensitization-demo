package org.example.desensitization.encrypt.repository;

import org.example.desensitization.encrypt.CipherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CipherDataRepository extends JpaRepository<CipherData, Integer> {
}
