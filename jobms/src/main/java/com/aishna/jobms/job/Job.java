package com.aishna.jobms.job;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="job_table")
@Data
public class Job {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Long companyId;

}
