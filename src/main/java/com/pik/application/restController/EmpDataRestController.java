package com.pik.application.restController;

import com.pik.application.domain.WorkReport;
import com.pik.application.repository.WorkReportRepository;
import com.pik.application.util.ProjectHoursWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmpDataRestController
{
    @Autowired
    WorkReportRepository workReportRepository;

    @RequestMapping(value = "/getProjects", method = RequestMethod.POST)
    public ResponseEntity<List<ProjectHoursWrapper>> getProjects(@RequestParam String username)
    {
        List<ProjectHoursWrapper> projectList = new ArrayList<>();

        List<WorkReport> workReportList = workReportRepository.findWorkReportsByUserOrderByProjectAsc(username);

        for(WorkReport wr: workReportList)
        {
            if(projectList.size() == 0)
            {
                ProjectHoursWrapper wrapper = new ProjectHoursWrapper();
                wrapper.setProject(wr.getProject());
                wrapper.setHours(wr.getHours());

                projectList.add(wrapper);
            }

            if(projectList.get(projectList.size() - 1).getProject() != wr.getProject())
            {
                ProjectHoursWrapper wrapper = new ProjectHoursWrapper();
                wrapper.setProject(wr.getProject());
                wrapper.setHours(wr.getHours());

                projectList.add(wrapper);
            }
            else
            {
                projectList.get(projectList.size() - 1).increaseHours(wr.getHours());
            }
        }

        return new ResponseEntity<List<ProjectHoursWrapper>>(projectList, HttpStatus.OK);
    }
}
