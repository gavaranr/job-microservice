package com.naveenx.jobms.job.mapper;

import com.naveenx.jobms.job.Job;
import com.naveenx.jobms.job.dto.JobDTO;
import com.naveenx.jobms.job.external.Company;
import com.naveenx.jobms.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO mapToJobWithCompanyReviewDto(Job job, Company company, List<Review> reviews) {

        JobDTO jobDTO = new JobDTO();

        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}