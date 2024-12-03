package com.example.backend.ResponseDTO;

import com.example.backend.Enum.PaymentFrequency;
import com.example.backend.Enum.PolicyType;
import com.example.backend.Model.Claim;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PolicyResponse {

    private int policyId;

    private String insurancePolicyNumber;

    private PolicyType policyType;

    private long policyCoverageAmount;

    private BigDecimal policyPremium;

    private PaymentFrequency paymentFrequency;

    private int policyDurationMonths;

    private LocalDate policyStartDate;

    private LocalDate policyEndDate;

    private List<Claim> claims;
}