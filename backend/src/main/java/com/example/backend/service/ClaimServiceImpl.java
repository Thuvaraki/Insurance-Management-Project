package com.example.backend.service;

import com.example.backend.enums.ClaimStatus;
import com.example.backend.exception.*;
import com.example.backend.model.Claim;
import com.example.backend.model.PolicyRegistration;
import com.example.backend.model.User;
import com.example.backend.repository.ClaimRepository;
import com.example.backend.repository.PolicyRegistrationRepository;
import com.example.backend.repository.PolicyRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PolicyRepository policyRepository;
    @Autowired
    PolicyRegistrationRepository policyRegistrationRepo;
    @Override
    public Claim createClaim(Claim claim) {
        User user = userRepository.findById(claim.getUser().getUserId())
                .orElseThrow(() -> new UserNotExistException("User not found with ID: " + claim.getUser().getUserId()));

        PolicyRegistration policyRegistration = policyRegistrationRepo.findById(claim.getRegisteredPolicy().getRegistrationId())
                .orElseThrow(() -> new PolicyNotEnrolledException("You haven't registered for the policy"));

        if (policyRegistration.getUser().getUserId() != user.getUserId()) {
            throw new UnauthorizedAccessException("User is not enrolled in this policy and cannot create a claim.");
        }
        return claimRepository.save(claim);
    }
    @Override
    public Claim getClaimById(int claimId) {
        Optional<Claim> claim = claimRepository.findById(claimId);
        return claim.orElseThrow(() -> new ClaimNotExistException("Claim not found"));
    }
    @Override
    public List<Claim> getClaimsByUserUserId(int userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotExistException("User not exist with Id: " + userId));
        List<Claim> Claims = claimRepository.findByUserUserId(userId);
        return Claims;
    }
    @Override
    public List<Claim> getUserClaimsByStatus(int userId, ClaimStatus status) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotExistException("User not exist with Id: " + userId));
        List<Claim> Claims = claimRepository.findByUserUserIdAndClaimStatus(userId, status);
        return Claims;
    }
    @Override
    public Claim updateClaim(int userId, int claimId, Claim claim) {
        Optional<Claim> existingClaim = claimRepository.findById(claimId);
        if (existingClaim.isEmpty()) {
            throw new ClaimNotExistException("Claim not found with ID: "+ claim.getClaimId());
        }
        if(existingClaim.get().getUser().getUserId() != userId){
            throw new UnauthorizedAccessException("You are trying to perform an unauthorized update");
        }
        Claim claimToUpdate = existingClaim.get();
        claimToUpdate.setClaimName(claim.getClaimName());
        claimToUpdate.setClaimDescription(claim.getClaimDescription());
        claimToUpdate.setSubmittedDate(claim.getSubmittedDate());
        claimToUpdate.setClaimAmount(claim.getClaimAmount());
        return claimRepository.save(claimToUpdate);
    }
    @Override
    public void deleteClaim(int userId, int claimId) {
        if (claimRepository.existsById(claimId) && claimRepository.findById(claimId).get().getUser().getUserId() == userId) {
            claimRepository.deleteById(claimId);
        } else {
            throw new ClaimNotExistException("Claim not found");
        }
    }
    @Override
    public List<Claim> getAllClaimsByStatus(ClaimStatus status) {
        List<Claim> Claims = claimRepository.findAllByClaimStatus(status);
        return Claims;
    }
    @Override
    public Claim adminUpdateClaim(int claimId, Claim claim) {
        Optional<Claim> existingClaim = claimRepository.findById(claimId);
        if (existingClaim.isPresent()) {
            Claim oldClaim = existingClaim.get();
            oldClaim.setClaimStatus(claim.getClaimStatus());
            oldClaim.setApprovedDate(new Date());
            return claimRepository.save(oldClaim);
        }
        throw new ClaimNotExistException("Claim not found with Id: " + claimId);
    }
}
