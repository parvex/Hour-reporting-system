// package com.pik.application.restController;

// import com.pik.application.domain.Project;
// import com.pik.application.service.ProjectService;
// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.HttpStatus;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import static org.junit.Assert.*;
// import static org.mockito.BDDMockito.given;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @RunWith(SpringRunner.class)
// @WebMvcTest
// @AutoConfigureMockMvc
// public class ProjectRestControllerTest {

//     @Autowired
//     private MockMvc mock;

//     @MockBean
//     private ProjectService projectService;
//     private Project newProject;
//     @Before
//     public void init(){
//          newProject = new Project();
//     }

//     @Test
//     public void getProjectName() throws Exception {

//         newProject.setName("TestProject"); newProject.setDescription("TestDescription");
//         projectService.createProject(newProject);

// //        given(projectService.createProject(newProject))

//         mock.perform(get("/api/unique-project-name")
//                 .param("name", "TestProject"))
//                 .andExpect(status().isBadRequest());

//     }

//     @After
//     public void clean(){
//         projectService.deleteProject(newProject.getId());
//     }
// }
