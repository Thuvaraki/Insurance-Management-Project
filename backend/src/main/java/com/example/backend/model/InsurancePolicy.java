package com.example.backend.model;

import com.example.backend.enums.PaymentFrequency;
import com.example.backend.enums.PolicyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "insurance_policies") // Specifies the table name as it to follow best practices [Pluralized, descriptive, concise, snake case]
@Getter
@Setter
public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private int policyId;

    @Column(name = "policy_number",nullable = false,unique=true)
    private String insurancePolicyNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type", nullable = false)
    private PolicyType policyType;

    @Column(name = "policy_coverage_amount", nullable = false)
    private long policyCoverageAmount; //amount the insurance company agrees to pay

    @Column(name = "policy_premium", nullable = false)
    private BigDecimal policyPremium; //amount the user pays periodically

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_frequency", nullable = false)
    private PaymentFrequency paymentFrequency;

    @Column(name = "policy_duration_months", nullable = false)
    private int policyDurationMonths;

    @OneToMany(mappedBy = "insurancePolicy", cascade = CascadeType.ALL)
    private List<PolicyRegistration> policyRegistrations;
}
