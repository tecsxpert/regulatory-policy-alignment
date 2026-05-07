package com.internship.tool.controller;

import com.internship.tool.entity.PolicyRecord;
import com.internship.tool.repository.PolicyRecordRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-records")
public class PolicyRecordController {

    private final PolicyRecordRepository policyRecordRepository;

    public PolicyRecordController(PolicyRecordRepository policyRecordRepository) {
        this.policyRecordRepository = policyRecordRepository;
    }

    @GetMapping
    public ResponseEntity<List<PolicyRecord>> getAllPolicies() {
        return ResponseEntity.ok(policyRecordRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPolicyById(@PathVariable Long id) {
        PolicyRecord policyRecord = policyRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy record not found with id: " + id));

        return ResponseEntity.ok(policyRecord);
    }

    @PostMapping
    public ResponseEntity<PolicyRecord> createPolicy(@Valid @RequestBody PolicyRecord policyRecord) {
        PolicyRecord savedPolicy = policyRecordRepository.save(policyRecord);
        return new ResponseEntity<>(savedPolicy, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePolicy(@PathVariable Long id,
                                          @Valid @RequestBody PolicyRecord updatedPolicy) {

        PolicyRecord existingPolicy = policyRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy record not found with id: " + id));

        existingPolicy.setTitle(updatedPolicy.getTitle());
        existingPolicy.setDescription(updatedPolicy.getDescription());
        existingPolicy.setDepartment(updatedPolicy.getDepartment());
        existingPolicy.setStatus(updatedPolicy.getStatus());

        PolicyRecord savedPolicy = policyRecordRepository.save(existingPolicy);
        return ResponseEntity.ok(savedPolicy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable Long id) {
        PolicyRecord policyRecord = policyRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy record not found with id: " + id));

        policyRecordRepository.delete(policyRecord);
        return ResponseEntity.ok("Policy record deleted successfully");
    }
}