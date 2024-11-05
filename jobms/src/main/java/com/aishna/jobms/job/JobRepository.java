package com.aishna.jobms.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    void deleteByCompanyId(Long companyId);
}
