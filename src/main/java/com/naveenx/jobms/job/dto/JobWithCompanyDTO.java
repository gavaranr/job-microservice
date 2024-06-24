package com.naveenx.jobms.job.dto;

import com.naveenx.jobms.job.Job;
import com.naveenx.jobms.job.external.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobWithCompanyDTO {
    private Job job;
    private Company company;
}
