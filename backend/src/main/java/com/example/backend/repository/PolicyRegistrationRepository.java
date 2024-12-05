package com.example.backend.repository;

import com.example.backend.model.Claim;
import com.example.backend.model.InsurancePolicy;
import com.example.backend.model.PolicyRegistration;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRegistrationRepository extends JpaRepository<PolicyRegistration,Integer> {
    List<PolicyRegistration> findByUserUserId(int userId);
    boolean existsByInsurancePolicyPolicyId(int policyId);
    @Query("SELECT c FROM Claim c WHERE c.registeredPolicy.insurancePolicy.policyId = :policyId")
    List<Claim> findClaimsByPolicyId(@Param("policyId") int policyId);

    boolean existsByUserAndInsurancePolicy(User user, InsurancePolicy insurancePolicy);
}
