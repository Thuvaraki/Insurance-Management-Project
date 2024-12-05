package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class PolicyRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registrationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private InsurancePolicy insurancePolicy;

    @Column(nullable = false)
    @NotNull
    private LocalDate policyStartDate;

    @Column(nullable = false)
    private LocalDate policyEndDate;

    @OneToMany(mappedBy = "registeredPolicy", cascade = CascadeType.ALL)
    private List<Claim> claims;
}
