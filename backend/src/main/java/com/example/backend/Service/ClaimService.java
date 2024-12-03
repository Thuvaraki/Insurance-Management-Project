package com.example.backend.Service;

import com.example.backend.Enum.ClaimStatus;
import com.example.backend.Model.Claim;

import java.util.List;

public interface ClaimService {
    Claim createClaim(Claim claim);
    Claim getClaimById(int claimId);
    List<Claim> getClaimsByUserUserId(int userId);
    List<Claim> getUserClaimsByStatus(int userId, ClaimStatus status);
    Claim updateClaim(int userId, int claimId, Claim claim);
    void deleteClaim(int userId, int claimId);
    List<Claim> getAllClaimsByStatus(ClaimStatus status);
    Claim adminUpdateClaim(int claimId, Claim claim);
}