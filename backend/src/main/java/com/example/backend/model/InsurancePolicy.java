package com.example.backend.model;

import com.example.backend.enums.PaymentFrequency;
import com.example.backend.enums.PolicyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policyId;

    @Column(nullable = false,unique=true) //database-level constraint
    @NotBlank
    private String insurancePolicyNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyType policyType;

    @Column(nullable = false)
    private long policyCoverageAmount; //amount the insurance company agrees to pay

    @Column(nullable = false)
    private BigDecimal policyPremium; //amount the user pays periodically

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentFrequency paymentFrequency;

    @Column(nullable = false)
    @Positive
    private int policyDurationMonths;

    @OneToMany(mappedBy = "insurancePolicy", cascade = CascadeType.ALL)
    private List<PolicyRegistration> policyRegistrations;
}
