package com.example.backend.Controller;

import com.example.backend.Exception.PolicyAlreadyExistException;
import com.example.backend.Exception.PolicyNotExistException;
import com.example.backend.Model.InsurancePolicy;
import com.example.backend.Service.InsurancePolicyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/policy")
public class InsurancePolicyController {
    @Autowired
    private InsurancePolicyServiceImpl insurancePolicyService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createInsurancePolicy(@RequestBody InsurancePolicy insurancePolicy) {
        try {
            InsurancePolicy createdInsurancePolicy = insurancePolicyService.createInsurancePolicy(insurancePolicy);
            return ResponseEntity.status(HttpStatus.CREATED).body("New policy created successfully!" + createdInsurancePolicy);
        }catch (PolicyAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get/{policyId}")
    public ResponseEntity<?> getInsurancePolicyById(@PathVariable int policyId) {
        try {
            InsurancePolicy fetchedInsurancePolicy = insurancePolicyService.getInsurancePolicyById(policyId);
            return ResponseEntity.status(HttpStatus.OK).body(fetchedInsurancePolicy);
        } catch (PolicyNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get_all")
    public ResponseEntity<?> getAllUInsurancePolicies() {
        try {
            List<InsurancePolicy> fetchedPolicies = insurancePolicyService.getAllUInsurancePolicies();
            return ResponseEntity.status(HttpStatus.OK).body(fetchedPolicies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{policyId}")
    public ResponseEntity<?> deleteInsurancePolicyById(@PathVariable int policyId) {
        try {
            insurancePolicyService.deleteInsurancePolicyById(policyId);
            return ResponseEntity.status(HttpStatus.OK).body("Policy successfully deleted!");
        } catch (PolicyNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{policyId}")
    public ResponseEntity<?> updateInsurancePolicy(@PathVariable int policyId, @RequestBody InsurancePolicy insurancePolicy) {
        try {
            InsurancePolicy updatedInsurancePolicy = insurancePolicyService.updateInsurancePolicy(policyId, insurancePolicy);
            return ResponseEntity.status(HttpStatus.OK).body("Policy updated successfully! " + updatedInsurancePolicy);
        } catch (PolicyNotExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
