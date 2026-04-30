package com.internship.tool.service;

import com.internship.tool.entity.PolicyRecord;
import com.internship.tool.repository.PolicyRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyRecordServiceTest {

    @Mock
    private PolicyRecordRepository repository;

    @InjectMocks
    private PolicyRecordService service;

    @Test
    void testSavePolicy() {
        PolicyRecord policy = new PolicyRecord();
        policy.setTitle("Policy 1");
        policy.setDescription("Test Description");
        policy.setDepartment("Compliance");
        policy.setStatus("Active");

        when(repository.save(policy)).thenReturn(policy);

        PolicyRecord result = service.savePolicy(policy);

        assertEquals("Policy 1", result.getTitle());
        assertEquals("Compliance", result.getDepartment());
    }

    @Test
    void testGetAllPolicies() {
        PolicyRecord p1 = new PolicyRecord();
        p1.setTitle("Policy 1");

        PolicyRecord p2 = new PolicyRecord();
        p2.setTitle("Policy 2");

        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<PolicyRecord> result = service.getAllPolicies();

        assertEquals(2, result.size());
    }

    @Test
    void testGetPolicyById() {
        PolicyRecord policy = new PolicyRecord();
        policy.setId(1L);
        policy.setTitle("Policy 1");

        when(repository.findById(1L)).thenReturn(Optional.of(policy));

        PolicyRecord result = service.getPolicyById(1L);

        assertEquals("Policy 1", result.getTitle());
    }

    @Test
    void testUpdatePolicy() {
        PolicyRecord existing = new PolicyRecord();
        existing.setId(1L);
        existing.setTitle("Old Title");
        existing.setDescription("Old Description");
        existing.setDepartment("Old Department");
        existing.setStatus("Old Status");

        PolicyRecord updated = new PolicyRecord();
        updated.setTitle("New Title");
        updated.setDescription("New Description");
        updated.setDepartment("New Department");
        updated.setStatus("New Status");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        PolicyRecord result = service.updatePolicy(1L, updated);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Department", result.getDepartment());
    }

    @Test
    void testDeletePolicy() {
        PolicyRecord policy = new PolicyRecord();
        policy.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(policy));

        service.deletePolicy(1L);

        verify(repository, times(1)).delete(policy);
    }
}