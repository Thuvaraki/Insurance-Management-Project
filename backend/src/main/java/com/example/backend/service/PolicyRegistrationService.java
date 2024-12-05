package com.example.backend.service;

import com.example.backend.model.Claim;
import com.example.backend.model.PolicyRegistration;
import com.example.backend.dto.response.PolicyResponse;

import java.util.List;

public interface PolicyRegistrationService {
    PolicyRegistration enrollPolicy(int userId, int insurancePolicyId);
    List<PolicyResponse> getEnrolledPolicies(int userId);
    List<Claim> findByPolicyRegistrationPolicyId(int policyId);
    List<Claim> getUserClaimsByPolicy(int userId, int policyId);
}
