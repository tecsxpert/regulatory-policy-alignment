package com.internship.tool.service;

import com.internship.tool.entity.PolicyRecord;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.PolicyRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyRecordService {

    @Autowired
    private PolicyRecordRepository policyRecordRepository;

    public List<PolicyRecord> getAllPolicies() {
        return policyRecordRepository.findAll();
    }

    public PolicyRecord getPolicyById(Long id) {
        return policyRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy with ID " + id + " not found"));
    }

    public PolicyRecord savePolicy(PolicyRecord policyRecord) {
        return policyRecordRepository.save(policyRecord);
    }

    public PolicyRecord updatePolicy(Long id, PolicyRecord updatedPolicy) {
        PolicyRecord existingPolicy = policyRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy with ID " + id + " not found"));

        existingPolicy.setTitle(updatedPolicy.getTitle());
        existingPolicy.setDescription(updatedPolicy.getDescription());
        existingPolicy.setDepartment(updatedPolicy.getDepartment());
        existingPolicy.setStatus(updatedPolicy.getStatus());

        return policyRecordRepository.save(existingPolicy);
    }

    public void deletePolicy(Long id) {
        PolicyRecord existingPolicy = policyRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy with ID " + id + " not found"));

        policyRecordRepository.delete(existingPolicy);
    }
}