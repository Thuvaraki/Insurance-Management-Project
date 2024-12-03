package com.example.backend.Repository;

import com.example.backend.Model.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<InsurancePolicy,Integer> {
    Optional<InsurancePolicy> findByInsurancePolicyNumber(String insurancePolicyNumber);
}
