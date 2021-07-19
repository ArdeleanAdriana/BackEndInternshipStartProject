package com.example.student;

import com.example.student.dto.StudentDto;
import com.example.student.service.StudentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentControllerTest {

    public MockMvc mockMvc;
    public ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @InjectMocks
    private StudentController studentController;

    @MockBean
    private StudentService studentService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        List<StudentDto> students = new ArrayList<>();
        students.add(new StudentDto(144L, "aaa", "adsa@yahoo.com", "bbbb", "0123456789"));
        students.add(new StudentDto(123L, "aacccaa", "blabla@gmail.com", "bbbb", "0123452789"));
        students.add(new StudentDto(200L, "bbb", "blablaaa@gmail.com", "bbbb", "0133456789"));
        when(studentService.findAllStudents()).thenReturn(students);
        MvcResult mvcResult = mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").hasJsonPath())
                .andExpect(jsonPath("$[0].email").hasJsonPath())
                .andExpect(jsonPath("$[0].university").hasJsonPath())
                .andExpect(jsonPath("$[0].phone").hasJsonPath())
                .andExpect(jsonPath("$[0].id").hasJsonPath())
                .andReturn();
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        StudentDto student = new StudentDto(144L, "aaa", "adsa@yahoo.com", "bbbb", "0123456789");
        when(studentService.findStudentById(144L)).thenReturn(student);
        MvcResult mvcResult = mockMvc.perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        StudentDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<StudentDto>() {
        });
        assertEquals(student, result);
    }

    @Test
    public void deleteStudentByIdTest() throws Exception {
        StudentDto student = new StudentDto(144L, "aaa", "adsa@yahoo.com", "bbbb", "0123456789");
        ResultActions result = mockMvc.perform(delete("/students/" + student.getId()));
        result.andExpect(status().isOk());

    }

    @Test
    public void addStudentTest() throws Exception {
        StudentDto student = new StudentDto(144L, "aaaavsadsavsadaaaa", "andi@gmail.com", "FSEGA", "0799999999");
        when(studentService.addStudent(student)).thenReturn(student);
        String jsonContent = "{\n" +
                "    \"id\": \"144\",\n" +
                "    \"name\": \"aaaavsadsavsadaaaa\",\n" +
                "    \"email\": \"andi@gmail.com\",\n" +
                "    \"university\": \"FSEGA\",\n" +
                "    \"phone\": \"0799999999\"\n" +
                "}";

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.university").value(student.getUniversity()))
                .andExpect(jsonPath("$.phone").value(student.getPhone()));


    }

    @Test
    public void updateStudentTest() throws Exception {
        StudentDto student = new StudentDto(144L, "aaaavsadsavsadaaaa", "andi@gmail.com", "FSEGA", "0799999999");

        when(studentService.updateStudent(student, 144L)).thenReturn(student);
        String jsonContent = "{\n" +
                "    \"id\": \"144\",\n" +
                "    \"name\": \"aaaavsadsavsadaaaa\",\n" +
                "    \"email\": \"andi@gmail.com\",\n" +
                "    \"university\": \"FSEGA\",\n" +
                "    \"phone\": \"0799999999\"\n" +
                "}";

        mockMvc.perform(put("/students/144")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.university").value(student.getUniversity()))
                .andExpect(jsonPath("$.phone").value(student.getPhone()));


    }

    @Test
    public void getAllStudents() throws Exception {
        List<StudentDto> students = new ArrayList<>();
        students.add(new StudentDto(144L, "aaa", "adsa@yahoo.com", "bbbb", "0123456789"));
        students.add(new StudentDto(123L, "aacccaa", "blabla@gmail.com", "bbbb", "0123452789"));
        students.add(new StudentDto(200L, "bbb", "blablaaa@gmail.com", "bbbb", "0133456789"));
        when(studentService.findAllStudents()).thenReturn(students);
        MvcResult mvcResult = mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<StudentDto> personDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<StudentDto>>() {
        });
        assertEquals(students, personDTOS);
    }


}