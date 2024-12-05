package com.example.backend.controller;

import com.example.backend.enums.ClaimStatus;
import com.example.backend.exception.*;
import com.example.backend.model.Claim;
import com.example.backend.service.ClaimServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/claims")
public class ClaimController {
    @Autowired
    private ClaimServiceImpl claimService;
    @PostMapping("/create")
    public ResponseEntity<?> createClaim(@RequestBody Claim claim) {
        try {
            Claim createdClaim = claimService.createClaim(claim);
            return ResponseEntity.status(HttpStatus.CREATED).body("Claim created successfully!" + createdClaim);
        }catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (PolicyNotEnrolledException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch ( UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/get/{claimId}")
    public ResponseEntity<?> getClaimById(@PathVariable int claimId) {
        try {
            Claim fetchedClaim = claimService.getClaimById(claimId);
            return ResponseEntity.status(HttpStatus.OK).body(fetchedClaim);
        }  catch (ClaimNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Claim not found with ID: " + claimId);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//     Retrieve all claims associated with a user
    @GetMapping("retrieve_claims/{userId}")
    public ResponseEntity<?> getClaimsByUserId(@PathVariable int userId) {
        try {
            List<Claim> claims = claimService.getClaimsByUserUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(claims);
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving claims: " + e.getMessage());
        }
    }

    // Retrieve claims for a user filtered by status
    @GetMapping("/claims/user/{userId}/status/{status}")
    public ResponseEntity<?> getUserClaimsByStatus(@PathVariable int userId, @PathVariable ClaimStatus status) {
        try {
            List<Claim> claims = claimService.getUserClaimsByStatus(userId, status);
            return ResponseEntity.status(HttpStatus.OK).body(claims);
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving claims: " + e.getMessage());
        }
    }
    @PutMapping("/update/{userId}/{claimId}")
    public ResponseEntity<?> updateClaim(@PathVariable int userId, @PathVariable int claimId, @RequestBody Claim claim) {
        try {
            Claim updatedClaim = claimService.updateClaim(userId, claimId, claim);
            return ResponseEntity.status(HttpStatus.OK).body("Claim updated successfully!" + updatedClaim);
        }catch ( UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Delete a claim
    @DeleteMapping("delete_claims/{userId}/{claimId}")
    public ResponseEntity<?> deleteClaim(@PathVariable int userId, @PathVariable int claimId) {
        try {
            claimService.deleteClaim(userId, claimId);
            return ResponseEntity.status(HttpStatus.OK).body("Claim deleted successfully.");
        } catch (ClaimNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Claim not found with ID: " + claimId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving claims: " + e.getMessage());
        }
    }

    // Retrieve all claims, optionally filtered by status
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("retrieve_claims/status/{status}")
    public ResponseEntity<?> getAllClaimsByStatus( @PathVariable ClaimStatus status) {
        try {
            List<Claim> claims = claimService.getAllClaimsByStatus(status);
            return ResponseEntity.status(HttpStatus.OK).body(claims);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving claims: " + e.getMessage());
        }
    }

    // Admin approve/reject a claim
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("manage_claims/{claimId}")
    public ResponseEntity<?> adminUpdateClaim(@PathVariable int claimId, @RequestBody Claim claim) {
        try {
            Claim updatedClaim = claimService.adminUpdateClaim(claimId, claim);
            return ResponseEntity.status(HttpStatus.OK).body(updatedClaim);
        } catch (ClaimNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Claim not found with ID: " + claimId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving claims: " + e.getMessage());
        }
    }
}

