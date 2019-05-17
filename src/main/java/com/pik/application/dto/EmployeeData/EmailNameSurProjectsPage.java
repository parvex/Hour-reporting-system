package com.pik.application.dto.EmployeeData;

import com.pik.application.dto.PageOptions;

public class EmailNameSurProjectsPage {

    private EmailNameSurProjects criteria;
    private PageOptions options;

    public EmailNameSurProjectsPage(EmailNameSurProjects criteria, PageOptions options) {
        this.criteria = criteria;
        this.options = options;
    }

    public EmailNameSurProjects getEmailNameSurProjects() {
        return criteria;
    }

    public void setEmailNameSurnameProjects(EmailNameSurProjects criteria) {
        this.criteria = criteria;
    }

    public PageOptions getPageOptions() {
        return options;
    }

    public void setPageOptions(PageOptions options) {
        this.options = options;
    }
}
