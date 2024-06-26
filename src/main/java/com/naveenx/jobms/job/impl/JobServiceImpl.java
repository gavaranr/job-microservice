package com.naveenx.jobms.job.impl;

import com.naveenx.jobms.job.Job;
import com.naveenx.jobms.job.JobRepository;
import com.naveenx.jobms.job.JobService;
import com.naveenx.jobms.job.clients.CompanyClient;
import com.naveenx.jobms.job.clients.ReviewClient;
import com.naveenx.jobms.job.dto.JobDTO;
import com.naveenx.jobms.job.external.Company;
import com.naveenx.jobms.job.external.Review;
import com.naveenx.jobms.job.mapper.JobMapper;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    public JobServiceImpl (JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public JobDTO getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId).orElse(null);

        assert job != null;
        return convertToDto(job);
    }
    @Override
    public void createJob(@NotNull Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean deleteJobById(Long id) {

        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {

        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
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

    private JobDTO convertToDto(Job job) {

        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyReviewDto(job, company, reviews);
    }
}
