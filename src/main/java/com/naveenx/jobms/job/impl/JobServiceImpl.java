package com.naveenx.jobms.job.impl;

import com.naveenx.jobms.job.Job;
import com.naveenx.jobms.job.JobRepository;
import com.naveenx.jobms.job.JobService;
import com.naveenx.jobms.job.dto.JobWithCompanyDTO;
import com.naveenx.jobms.job.external.Company;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    public JobServiceImpl (JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertToDto)
                .toList();
    }

    private JobWithCompanyDTO convertToDto(Job job) {

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);

        Company company = restTemplate
                .getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class);

        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }

    @Override
    public Job getJobById(Long id) {

        return jobRepository.findById(id).orElse(null);
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
}
