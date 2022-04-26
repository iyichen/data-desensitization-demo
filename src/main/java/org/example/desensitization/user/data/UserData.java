package org.example.desensitization.user.data;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_data")
public class UserData {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "userDataID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "userDataID", strategy = "assigned")
    private int id;

    /**
     * 用户姓名
     */
    @Column(name = "name", nullable = false, length = 20)
    private String name = "";

    /**
     * 用户姓名密文ID
     */
    @Column(name = "name_cipher_id", nullable = false)
    private int nameCipherId = 0;

    /**
     * 用户姓名密文内容
     */
    @Column(name = "name_cipher_text", nullable = false)
    private String nameCipherText = "";

    /**
     * 用户身份证
     */
    @Column(name = "id_card", nullable = false, length = 20)
    private String idCard = "";

    /**
     * 用户身份证密文ID
     */
    @Column(name = "id_card_cipher_id", nullable = false)
    private int idCardCipherId = 0;

    /**
     * 用户身份证密文内容
     */
    @Column(name = "id_card_cipher_text", nullable = false)
    private String idCardCipherText = "";

}
