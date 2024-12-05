package com.example.backend.service;

import com.example.backend.model.InsurancePolicy;

import java.util.List;

public interface InsurancePolicyService {
    InsurancePolicy createInsurancePolicy(InsurancePolicy insurancePolicy);
    InsurancePolicy getInsurancePolicyById(int policyId);
    List<InsurancePolicy> getAllUInsurancePolicies();
    void deleteInsurancePolicyById(int policyId);
    InsurancePolicy updateInsurancePolicy(int policyId, InsurancePolicy insurancePolicy);
}
