package org.example.desensitization.encrypt;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cipher_data")
public class CipherData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 初始向量
     */
    @Column(name = "iv", nullable = false)
    private String iv;

    /**
     * 密钥
     */
    @Column(name = "secret_key", nullable = false)
    private String secretKey;

}
