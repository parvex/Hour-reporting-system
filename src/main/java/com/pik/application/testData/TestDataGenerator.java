package com.pik.application.testData;

import com.pik.application.domain.Project;
import com.pik.application.domain.SystemRole;
import com.pik.application.domain.User;
import com.pik.application.domain.WorkReport;
import com.pik.application.repository.ProjectRepository;
import com.pik.application.repository.UserRepository;
import com.pik.application.repository.WorkReportRepository;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class TestDataGenerator
{
    private List<Project> projects;

    private List<User> employees;

    private UserRepository userRepository;

    private ProjectRepository projectRepository;

    private WorkReportRepository workReportRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TestDataGenerator(UserRepository userRepository, ProjectRepository projectRepository,
                             WorkReportRepository workReportRepository)
    {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.workReportRepository = workReportRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event)
    {
        List<User> users1 = userRepository.findAll();
        List<Project> projects1 = projectRepository.findAll();
        List<WorkReport> wr = workReportRepository.findAll();

        if(users1.isEmpty())
            seedProject();
        if(projects1.isEmpty())
            seedUser();
        if(wr.isEmpty())
            seedWorkReport();
    }

    private void seedUser()
    {
        DataFactory df = new DataFactory();
        Date minDate = df.getDate(2000, 1, 1);
        Date maxSupDate = df.getDate(2016, 1, 1);
        Date maxDate = new Date();

        User boss = new User();
        boss.setName(df.getFirstName());
        boss.setSurname(df.getLastName());
        boss.setEmail(df.getEmailAddress());
        boss.setPasswordHash(df.getRandomWord());
        boss.setSystemRole(SystemRole.SUPERVISOR);
        boss.setRegisteredAt(minDate);
        boss.setSupervisor(null);
        userRepository.save(boss);

        List<User> supervisors = new LinkedList<User>();

        User user;

        for(int i = 0; i < 25; i++)
        {
            user = new User();
            user.setName(df.getFirstName());
            user.setSurname(df.getLastName());
            user.setEmail(df.getEmailAddress());
            user.setPasswordHash(df.getRandomWord());
            user.setSystemRole(SystemRole.SUPERVISOR);
            user.setRegisteredAt(df.getDateBetween(minDate, maxSupDate));
            user.setSupervisor(boss);

            Set<Project> supProjects = new HashSet<Project>();
            supProjects.add(projects.get(df.getNumberUpTo(projects.size())));
            supProjects.add(projects.get(df.getNumberUpTo(projects.size())));
            user.setProjects(supProjects);

            supervisors.add(user);
            userRepository.save(user);
        }

        employees = new LinkedList<User>();

        for(int i = 0; i < 250; i++)
        {
            user = new User();
            user.setName(df.getFirstName());
            user.setSurname(df.getLastName());
            user.setEmail(df.getEmailAddress());
            user.setPasswordHash(df.getRandomWord());
            user.setSystemRole(SystemRole.EMPLOYEE);
            user.setRegisteredAt(df.getDateBetween(minDate,maxDate));
            user.setSupervisor(supervisors.get(df.getNumberUpTo(supervisors.size())));

            Set<Project> usrProjects = user.getSupervisor().getProjects();
            usrProjects.add(projects.get(df.getNumberUpTo(projects.size())));
            user.setProjects(usrProjects);

            employees.add(user);
            userRepository.save(user);
        }

        List<User> test = userRepository.findAll();
        log.info("Number of added users: " + test.size());
        log.info("Seeded Users.");
    }

    private void seedProject()
    {
        DataFactory df = new DataFactory();
        projects = new LinkedList<Project>();

        for(int i = 0; i < 50; i++)
        {
            Project project = new Project();
            project.setName(df.getRandomWord());
            project.setDescription(df.getRandomText(10, 100));

            projects.add(project);
            projectRepository.save(project);
        }

        log.info("Seeded Projects.");
    }

    private void seedWorkReport()
    {
        DataFactory df = new DataFactory();
        Date minDate = df.getDate(2017, 1, 1);
        Date maxDate = new Date();

        for(User emp: employees)
        {
            WorkReport workReport = new WorkReport();
            workReport.setDate(df.getDateBetween(minDate,maxDate));
            workReport.setReportedAt(workReport.getDate());
            workReport.setHours(df.getNumberUpTo(8));
            workReport.setAccepted(true);
            workReport.setUser(emp);
            workReport.setComment(df.getRandomText(10, 100));
            Project project = emp.getProjects().iterator().next();
            workReport.setProject(project);
            workReportRepository.save(workReport);
        }

        log.info("Seeded Work Reports.");
    }
}
