package com.example.backend.model;

import com.example.backend.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) //instructing JPA on how to store the enum's value in the database
    @Column(nullable = false)
    private Role role;

    private String dateOfBirth;

    private String address;

    @Column(nullable = false)
    private long contactInformation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Claim> claims;

}
