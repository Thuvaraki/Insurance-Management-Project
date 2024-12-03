package com.example.backend.Service;

import com.example.backend.Model.Claim;
import com.example.backend.Model.PolicyRegistration;
import com.example.backend.ResponseDTO.PolicyResponse;

import java.util.List;

public interface PolicyRegistrationService {
    PolicyRegistration enrollPolicy(int userId, int insurancePolicyId);
    List<PolicyResponse> getEnrolledPolicies(int userId);
    List<Claim> findByPolicyRegistrationPolicyId(int policyId);
    List<Claim> getUserClaimsByPolicy(int userId, int policyId);
}
