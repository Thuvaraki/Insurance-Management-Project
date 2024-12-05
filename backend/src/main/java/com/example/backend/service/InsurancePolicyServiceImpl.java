package com.example.backend.service;

import com.example.backend.exception.PolicyAlreadyExistException;
import com.example.backend.exception.PolicyNotExistException;
import com.example.backend.model.InsurancePolicy;
import com.example.backend.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {
    @Autowired
    PolicyRepository policyRepository;

    @Override
    public InsurancePolicy createInsurancePolicy(InsurancePolicy insurancePolicy) {
        Optional<InsurancePolicy> existingPolicy = policyRepository.findByInsurancePolicyNumber(insurancePolicy.getInsurancePolicyNumber());
        if (existingPolicy.isPresent()) {
            throw new PolicyAlreadyExistException("Policy already created with th same insurance policy number");
        }
        return policyRepository.save(insurancePolicy);
    }

    @Override
    public InsurancePolicy getInsurancePolicyById(int policyId) {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotExistException("Policy not found with ID: " + policyId));
    }

    @Override
    public List<InsurancePolicy> getAllUInsurancePolicies() {
        return policyRepository.findAll();
    }

    @Override
    public void deleteInsurancePolicyById(int policyId) {
        Optional<InsurancePolicy> fetchedPolicy = policyRepository.findById(policyId);
        if (fetchedPolicy.isPresent()) {
            policyRepository.deleteById(policyId);
        } else {
            throw new PolicyNotExistException("Policy not found with ID: " + policyId);
        }
    }

    @Override
    public InsurancePolicy updateInsurancePolicy(int policyId, InsurancePolicy insurancePolicy) {
        Optional<InsurancePolicy> existingPolicy = policyRepository.findById(policyId);
        if (existingPolicy.isPresent()) {
            InsurancePolicy policyToUpdate = existingPolicy.get();

            policyToUpdate.setInsurancePolicyNumber(insurancePolicy.getInsurancePolicyNumber());
            policyToUpdate.setPolicyPremium(insurancePolicy.getPolicyPremium());
            policyToUpdate.setPolicyType(insurancePolicy.getPolicyType());
            policyToUpdate.setPolicyCoverageAmount(insurancePolicy.getPolicyCoverageAmount());
            policyToUpdate.setPaymentFrequency(insurancePolicy.getPaymentFrequency());
            policyToUpdate.setPolicyDurationMonths(insurancePolicy.getPolicyDurationMonths());


            return policyRepository.save(policyToUpdate);
        }else {
            throw new PolicyNotExistException("Policy not found with ID: " + policyId);
        }
    }
}
