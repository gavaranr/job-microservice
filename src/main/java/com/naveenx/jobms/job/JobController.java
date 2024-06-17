package com.naveenx.jobms.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll () {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById (@PathVariable Long id) {

        Job job = jobService.getJobById(id);

        return (job!=null) ?
                new ResponseEntity<>(job, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createJob (@RequestBody Job job) {
        jobService.createJob(job);

//        Company c = job.getCompany();

        return new ResponseEntity<>
                ("Job added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById (@PathVariable Long id) {

        boolean delete = jobService.deleteJobById(id);

        return delete? new ResponseEntity<>
                ("Job deleted successfully", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateJob (@PathVariable Long id, @RequestBody Job updatedJob) {

        boolean updated = jobService.updateJob(id, updatedJob);

        return updated? new ResponseEntity<>
                ("Job updated successfully", HttpStatus.OK) :
                new ResponseEntity<>
                        ("Job doesn't exist", HttpStatus.NOT_FOUND);
    }
}