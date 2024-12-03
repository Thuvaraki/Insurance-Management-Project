package com.example.backend.Service;

import com.example.backend.Exception.PolicyNotExistException;
import com.example.backend.Exception.UserNotExistException;
import com.example.backend.Model.Claim;
import com.example.backend.Model.InsurancePolicy;
import com.example.backend.Model.PolicyRegistration;
import com.example.backend.Model.User;
import com.example.backend.Repository.PolicyRegistrationRepository;
import com.example.backend.Repository.PolicyRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.ResponseDTO.PolicyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class PolicyRegistrationServiceImpl implements PolicyRegistrationService {
    @Autowired
    PolicyRegistrationRepository policyRegistrationRepo;
    @Autowired
    PolicyRepository policyRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public PolicyRegistration enrollPolicy(int userId, int insurancePolicyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("User not found with ID: " + userId));
        InsurancePolicy insurancePolicy = policyRepository.findById(insurancePolicyId)
                .orElseThrow(() -> new PolicyNotExistException("Insurance policy not found with ID: " + insurancePolicyId));
        boolean alreadyEnrolled = policyRegistrationRepo.existsByUserAndInsurancePolicy(user, insurancePolicy);
        if (alreadyEnrolled) {
            throw new RuntimeException("User is already enrolled in this policy.");
        }

        PolicyRegistration newRegistration = new PolicyRegistration();
        newRegistration.setUser(user);
        newRegistration.setInsurancePolicy(insurancePolicy);
        newRegistration.setPolicyStartDate(LocalDate.now());
        newRegistration.setPolicyEndDate(LocalDate.now().plusMonths(insurancePolicy.getPolicyDurationMonths()));

        return policyRegistrationRepo.save(newRegistration);
    }
    @Override
    public List<PolicyResponse> getEnrolledPolicies(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("User not exist with Id: " + userId));

        List<PolicyRegistration> policies = policyRegistrationRepo.findByUserUserId(userId);

        return policies.stream().map(policyRegistration -> {
            InsurancePolicy insurancePolicy = policyRegistration.getInsurancePolicy();
            PolicyResponse policyResponse = new PolicyResponse();

            policyResponse.setPolicyId(insurancePolicy.getPolicyId());
            policyResponse.setInsurancePolicyNumber(insurancePolicy.getInsurancePolicyNumber());
            policyResponse.setPolicyType(insurancePolicy.getPolicyType());
            policyResponse.setPolicyCoverageAmount(insurancePolicy.getPolicyCoverageAmount());
            policyResponse.setPolicyPremium(insurancePolicy.getPolicyPremium());
            policyResponse.setPaymentFrequency(insurancePolicy.getPaymentFrequency());
            policyResponse.setPolicyDurationMonths(insurancePolicy.getPolicyDurationMonths());
            policyResponse.setPolicyStartDate(policyRegistration.getPolicyStartDate());
            policyResponse.setPolicyEndDate(policyRegistration.getPolicyEndDate());
            policyResponse.setClaims(policyRegistration.getClaims());

            return policyResponse;
        }).toList();
    }
    @Override
    public List<Claim> getUserClaimsByPolicy(int userId, int policyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("User not found with ID: " + userId));
        InsurancePolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotExistException("Policy not found with ID: " + policyId));

        List<PolicyRegistration> matchingRegistrations = policyRegistrationRepo
                .findByUserUserId(userId)
                .stream()
                .filter(reg -> reg.getInsurancePolicy().getPolicyId() == policyId)
                .toList();

        if (matchingRegistrations.isEmpty()) {
            throw new IllegalArgumentException("Policy not enrolled by user");
        }

        return matchingRegistrations.stream()
                .flatMap(reg -> reg.getClaims().stream())
                .toList();
    }
    @Override
    public List<Claim> findByPolicyRegistrationPolicyId(int policyId) {
        if (!policyRegistrationRepo.existsByInsurancePolicyPolicyId(policyId)) {
            throw new PolicyNotExistException("Policy not found with ID: " + policyId);
        }
        return policyRegistrationRepo.findClaimsByPolicyId(policyId);
    }
}
