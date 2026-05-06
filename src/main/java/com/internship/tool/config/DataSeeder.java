package com.internship.tool.config;

import com.internship.tool.entity.PolicyRecord;
import com.internship.tool.repository.PolicyRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PolicyRecordRepository policyRecordRepository;

    public DataSeeder(PolicyRecordRepository policyRecordRepository) {
        this.policyRecordRepository = policyRecordRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (policyRecordRepository.count() == 0) {

            for (int i = 1; i <= 30; i++) {
                PolicyRecord policy = new PolicyRecord();

                policy.setTitle("Policy Record " + i);
                policy.setDescription("Sample description for policy record " + i);
                policy.setDepartment(getDepartment(i));
                policy.setStatus(getStatus(i));

                policyRecordRepository.save(policy);
            }

            System.out.println("✅ 30 sample policy records inserted successfully.");
        } else {
            System.out.println("ℹ️ Data already exists. Seeder skipped.");
        }
    }

    private String getDepartment(int i) {
        switch (i % 5) {
            case 0:
                return "IT";
            case 1:
                return "HR";
            case 2:
                return "Finance";
            case 3:
                return "Legal";
            default:
                return "Compliance";
        }
    }

    private String getStatus(int i) {
        if (i % 3 == 0) {
            return "PENDING";
        } else if (i % 3 == 1) {
            return "ACTIVE";
        } else {
            return "INACTIVE";
        }
    }
}