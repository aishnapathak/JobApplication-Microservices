package com.aishna.jobms.job.impl;

import com.aishna.jobms.job.Job;
import com.aishna.jobms.job.JobRepository;
import com.aishna.jobms.job.JobService;
import com.aishna.jobms.job.clients.CompanyClient;
import com.aishna.jobms.job.clients.ReviewClient;
import com.aishna.jobms.job.dto.JobDTO;
import com.aishna.jobms.job.external.Company;
import com.aishna.jobms.job.external.Review;
import com.aishna.jobms.job.mapper.JobMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job) {
            Company company = companyClient.getCompany(job.getCompanyId());
            List<Review> reviews= reviewClient.getReviews(job.getCompanyId());

            JobDTO jobDTO = JobMapper.mapToJobWithCompanyDTO(job, company, reviews);
            return jobDTO;

    }
    @Override
    public void createJob(Job job) {
            jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e) {
            return false;
        }
    }


    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()) {
            Job job= jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
                return true;
            }
        return false;
    }
}
