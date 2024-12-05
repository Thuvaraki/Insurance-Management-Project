package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.Date;

import com.example.backend.enums.ClaimStatus;

@Entity
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimId;

    @Column(nullable = false) //database-level constraint
    private  String claimName;

    private String claimDescription;

    @Column(nullable = false)
    private Date submittedDate;

    private Date approvedDate;

    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    @Column(nullable = false)
    @Positive
    private int claimAmount;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private PolicyRegistration registeredPolicy;
}
