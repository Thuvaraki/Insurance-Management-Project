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
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name",nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) //instructing JPA on how to store the enum's value in the database
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "user_dob")
    private String dateOfBirth;

    @Column(name = "user_address")
    private String address;

    @Column(name = "user_contact_info",nullable = false)
    private long contactInformation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Claim> claims;

}
