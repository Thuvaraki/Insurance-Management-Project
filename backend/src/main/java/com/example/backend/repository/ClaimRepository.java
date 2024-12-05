package com.example.backend.repository;

import com.example.backend.enums.ClaimStatus;
import com.example.backend.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
    List<Claim> findByUserUserId(int userId);
    List<Claim> findAllByClaimStatus(ClaimStatus status);
    List<Claim> findByUserUserIdAndClaimStatus(int userId, ClaimStatus status);
}
