package com.example.backend.controller;
import com.example.backend.exception.PolicyNotExistException;
import com.example.backend.exception.UserNotExistException;
import com.example.backend.model.Claim;
import com.example.backend.model.PolicyRegistration;
import com.example.backend.dto.response.PolicyResponse;
import com.example.backend.service.PolicyRegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/policy_registration")
public class PolicyRegistrationController {
    @Autowired
    PolicyRegistrationServiceImpl policyRegistrationService;

    // Enroll a user in a new insurance policy
    @PostMapping("/enroll-policy/{userId}")
    public ResponseEntity<?> enrollPolicy(@PathVariable int userId, @RequestParam int insurancePolicyId) {
        try {
            PolicyRegistration enrolledPolicy = policyRegistrationService.enrollPolicy(userId, insurancePolicyId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Policy enrolled successfully!");
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (PolicyNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error enrolling policy: " + e.getMessage());
        }
    }

    // Retrieve all policies a user is enrolled in
    @GetMapping("enrolled-policies/{userId}")
    public ResponseEntity<?> getEnrolledPolicies(@PathVariable int userId) {
        try {
            List<PolicyResponse> policies = policyRegistrationService.getEnrolledPolicies(userId);
            return ResponseEntity.status(HttpStatus.OK).body(policies);
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving policies: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/policy/{policyId}")
    public ResponseEntity<?> getUserClaimsByPolicy(@PathVariable int userId, @PathVariable int policyId) {
        try {
            List<Claim> claims =  policyRegistrationService.getUserClaimsByPolicy(userId, policyId);
            return ResponseEntity.status(HttpStatus.OK).body(claims);
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        } catch (PolicyNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found with ID: " + policyId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving claims: " + e.getMessage());
        }
    }

    // Retrieve all claims for a specific insurance policy
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/retrieve_claims/policy/{policyId}")
    public ResponseEntity<?> findByPolicyRegistrationPolicyId(@PathVariable int policyId) {
        try {
            List<Claim> claims = policyRegistrationService.findByPolicyRegistrationPolicyId(policyId);
            return ResponseEntity.status(HttpStatus.OK).body(claims);
        } catch (PolicyNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found with ID: " + policyId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving claims: " + e.getMessage());
        }
    }
}