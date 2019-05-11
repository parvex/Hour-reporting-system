package com.pik.application.restController;

import com.pik.application.domain.WorkReport;
import com.pik.application.repository.WorkReportRepository;
import com.pik.application.util.ProjectHoursWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmpDataRestController
{
    @Autowired
    WorkReportRepository workReportRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/getProjects", method = RequestMethod.POST)
    public ResponseEntity<List<ProjectHoursWrapper>> getProjects(@RequestParam String username)
    {
        List<ProjectHoursWrapper> projectList = new ArrayList<>();

        List<WorkReport> workReportList = workReportRepository.findWorkReportsByUserOrderByProjectAsc(username);

        for(WorkReport report: workReportList)
        {
            log.info(report.getUser().getUsername());
        }

        for(WorkReport wr: workReportList)
        {
            if(projectList.size() == 0)
            {
                ProjectHoursWrapper wrapper = new ProjectHoursWrapper();
                wrapper.setName(wr.getProject().getName());
                wrapper.setHours(wr.getHours());

                projectList.add(wrapper);
            }

            if(projectList.get(projectList.size() - 1).getName().equals(wr.getProject().getName()))
            {
                ProjectHoursWrapper wrapper = new ProjectHoursWrapper();
                wrapper.setName(wr.getProject().getName());
                wrapper.setHours(wr.getHours());

                projectList.add(wrapper);
            }
            else
            {
                projectList.get(projectList.size() - 1).increaseHours(wr.getHours());
            }
        }

        if(projectList.isEmpty())
        {
            return new ResponseEntity<List<ProjectHoursWrapper>>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<List<ProjectHoursWrapper>>(projectList, HttpStatus.OK);
        }
    }
}
