package com.pik.application.testData;

import com.pik.application.domain.Project;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public TestDataGenerator(UserRepository userRepository, ProjectRepository projectRepository,
                             WorkReportRepository workReportRepository)
    {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.workReportRepository = workReportRepository;
        this.employees = new ArrayList<User>();
        this.projects = new ArrayList<Project>();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @EventListener
    public void seed(ContextRefreshedEvent event)
    {
        Optional<User> users1 = userRepository.getFirst();
        Optional<Project> projects1 = projectRepository.getFirst();
        Optional<WorkReport> wr = workReportRepository.getFirst();

        if(projects1.isEmpty())
            seedProject();
        if(users1.isEmpty()) {
            seedUser();
            addLeave();
        }
        if(wr.isEmpty())
            seedWorkReport();
    }

    private void seedUser()
    {
        DataFactory df = new DataFactory();
        Date minDate = df.getDate(2000, 1, 1);
        Date maxSupDate = df.getDate(2016, 1, 1);
        Date maxDate = new Date();

        int index = 0;

        User boss = new User();
        boss.setName("admin");
        boss.setSurname("admin");
        boss.setUsername("admin");
        boss.setEmail("admin@admin.admin");
        boss.setPassword(passwordEncoder.encode("admin"));
        boss.setRoles( Arrays.asList("SUPERVISOR" , "USER", "ADMIN"));
        boss.setRegisteredAt(minDate);
        boss.setSupervisor(null);
        userRepository.save(boss);

        List<User> supervisors = new LinkedList<User>();

        User user;

        if(projects.isEmpty())
        {
            projects = projectRepository.findAll();
        }

        for(int i = 0; i < 17; i++)
        {
            ++index;
            user = new User();
            user.setName(df.getFirstName());
            user.setSurname(df.getLastName());
            user.setEmail(df.getEmailAddress());
            user.setUsername(df.getRandomWord() + index);

            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles( Arrays.asList("SUPERVISOR" , "USER"));
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

        for(int i = 0; i < 100; i++)
        {
            ++index;
            user = new User();
            user.setName(df.getFirstName());
            user.setSurname(df.getLastName());
            user.setEmail(df.getEmailAddress());
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles( Arrays.asList("USER"));
            user.setUsername(df.getRandomWord() + index);

            user.setRegisteredAt(df.getDateBetween(minDate,maxDate));
            user.setSupervisor(supervisors.get(df.getNumberUpTo(supervisors.size())));

            Set<Project> usrProjects = user.getSupervisor().getProjects();
            usrProjects.add(projects.get(df.getNumberUpTo(projects.size())));
            user.setProjects(usrProjects);

            employees.add(user);
            userRepository.save(user);
        }

        log.info("Seeded Users.");
    }

    private void seedProject()
    {
        DataFactory df = new DataFactory();
        projects = new LinkedList<Project>();

        List<String> names = new ArrayList<String>();

        names.add("Project Alpha");
        names.add("Project Bravo");
        names.add("Project Charlie");
        names.add("Project Delta");
        names.add("Project Echo");
        names.add("Project Foxtrot");
        names.add("Project Golf");
        names.add("Project Hotel");
        names.add("Project India");
        names.add("Project Juliett");
        names.add("Project Kilo");
        names.add("Project Lima");
        names.add("Project Mike");
        names.add("Project November");
        names.add("Project Oscar");
        names.add("Project Papa");
        names.add("Project Quebec");
        names.add("Project Romeo");
        names.add("Project Sierra");
        names.add("Project Tango");
        names.add("Project Uniform");
        names.add("Project Victor");
        names.add("Project Whiskey");
        names.add("Project X-ray");
        names.add("Project Yankee");
        names.add("Project Zulu");


        for(String name: names)
        {
            Project project = new Project();
            project.setName(name);
            project.setDescription("This is a test project with a test description.");

            projects.add(project);
            projectRepository.save(project);
        }

        log.info("Seeded Projects.");
    }

    private void seedWorkReport()
    {
        DataFactory df = new DataFactory();
        Date minDate = df.getDate(2019, 1, 1);
        Date maxDate = new Date();

        for(User emp: employees)
        {
            for(int i = 0; i < 5; i++)
            {
                WorkReport workReport = new WorkReport();
                workReport.setDate(df.getDateBetween(minDate, maxDate));
                workReport.setReportedAt(workReport.getDate());
                workReport.setHours(df.getNumberUpTo(8));
                workReport.setAccepted(true);
                workReport.setUser(emp);
                workReport.setComment(df.getRandomText(10, 100));
                Project project = emp.getProjects().iterator().next();
                workReport.setProject(project);
                workReportRepository.save(workReport);
            }
        }

        log.info("Seeded Work Reports.");
    }

    private void addLeave()
    {
        Project leave = new Project();
        leave.setName("Employee Leave");
        leave.setDescription("Dummy project for tracking employees' days off.");
        projectRepository.save(leave);
        List<User> usersTMP = userRepository.findAll();

        for(User usr: usersTMP)
        {
            usr.getProjects().add(leave);
            userRepository.save(usr);
        }
    }
}
