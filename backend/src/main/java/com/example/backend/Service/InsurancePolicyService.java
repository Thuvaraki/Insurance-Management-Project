package com.example.backend.Service;

import com.example.backend.Model.InsurancePolicy;

import java.util.List;

public interface InsurancePolicyService {
    InsurancePolicy createInsurancePolicy(InsurancePolicy insurancePolicy);
    InsurancePolicy getInsurancePolicyById(int policyId);
    List<InsurancePolicy> getAllUInsurancePolicies();
    void deleteInsurancePolicyById(int policyId);
    InsurancePolicy updateInsurancePolicy(int policyId, InsurancePolicy insurancePolicy);
}
