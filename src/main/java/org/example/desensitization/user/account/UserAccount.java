package org.example.desensitization.user.account;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

}
