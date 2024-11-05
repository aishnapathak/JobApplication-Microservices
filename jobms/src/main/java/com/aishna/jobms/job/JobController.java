package com.aishna.jobms.job;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8083/companies", Company.class);
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id){

        Job job= jobService.getJobById(id);
        if(job!=null)
            return new ResponseEntity<>(job, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean deleted= jobService.deleteJobById(id);
        if(deleted){
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/jobs/{id}")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job updatedJob){
        boolean updates = jobService.updateJob(id, updatedJob);
        if(updates){
            return ResponseEntity.ok("Job updated successfully");
        }
        else{
            return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
        }
    }
}
