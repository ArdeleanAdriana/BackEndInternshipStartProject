package com.example.student.service;

import com.example.student.dto.StudentDto;
import com.example.student.exception.UserNotFoundException;
import com.example.student.model.Student;
import com.example.student.repo.StudentRepo;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    public static final Long ID = 1L;
    @InjectMocks
    StudentService studentService;
    @Mock
    StudentRepo studentRepo;
    @Mock
    ModelMapper modelMapper;


    @Test
    public void addStudentTest() {
        StudentDto studentDto = StudentDto.builder()
                .email("adsa@yahoo.com")
                .name("aaa")
                .phone("0123456789")
                .university("aaaa")
                .build();

        Student student = Student.builder()
                .id(ID)
                .name("aaa")
                .email("adsa@yahoo.com")
                .university("aaaa")
                .phone("0123456789")
                .build();

        when(studentRepo.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(studentDto, Student.class)).thenReturn(student);
        studentDto.setId(ID);
        when(modelMapper.map(student, StudentDto.class)).thenReturn(studentDto);

        StudentDto result = studentService.addStudent(studentDto);

        verify(studentRepo).save(any(Student.class));

        assertEquals(studentDto, result);

    }

    @Test
    public void findAllStudentsTest(){

        Student student = Student.builder()
                .id(500L)
                .name("aaa")
                .email("adsa@yahoo.com")
                .university("aaaa")
                .phone("0123456789")
                .build();

        Student student2 = Student.builder()
                .id(501L)
                .name("aaa")
                .email("adsa@yahoo.com")
                .university("aaaa")
                .phone("0123456789")
                .build();


        List<Student> students = Arrays.asList(student, student2);

        when(studentRepo.findAll()).thenReturn(students);
        List<StudentDto> resultedValue = studentService.findAllStudents();
        Assertions.assertEquals(students.size(), resultedValue.size());
    }


    @Test
    public void updateStudentTest() {
        StudentDto studentDto = StudentDto.builder()
                .email("adsa@yahoo.com")
                .name("aaa")
                .phone("0123456789")
                .university("aaaa")
                .build();

        Student student = Student.builder()
                .id(ID)
                .name("aaa")
                .email("adsa@yahoo.com")
                .university("aaaa")
                .phone("0123456789")
                .build();

        when(studentRepo.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(studentDto, Student.class)).thenReturn(student);
        studentDto.setId(ID);
        when(modelMapper.map(student, StudentDto.class)).thenReturn(studentDto);

        StudentDto result = studentService.updateStudent(studentDto);

        verify(studentRepo).save(any(Student.class));

        assertEquals(studentDto, result);

    }

    @Test
    public void deleteStudentTest(){
        Student student = Student.builder()
                .id(ID)
                .name("aaa")
                .email("adsa@yahoo.com")
                .university("aaaa")
                .phone("0123456789")
                .build();
        studentService.deleteStudent(student.getId());
        verify(studentRepo).deleteStudentById(student.getId());
    }
    @Test(expected = UserNotFoundException.class)
    public void testFindByIdException() {
        when(studentRepo.findStudentById(anyLong())).thenReturn(Optional.empty());
        studentService.findStudentById(1L);
    }


    @Test
    public void findStudentByIdTest(){
        Student student = Student.builder()
                .id(ID)
                .name("aaa")
                .email("adsa@yahoo.com")
                .university("aaaa")
                .phone("0123456789")
                .build();
        StudentDto studentDto = StudentDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .name(student.getName())
                .phone(student.getPhone())
                .university(student.getUniversity())
                .build();

        when(studentRepo.findStudentById(anyLong())).thenReturn(Optional.of(student));
        when(modelMapper.map(student, StudentDto.class)).thenReturn(studentDto);

        StudentDto fetchedStudent = studentService.findStudentById(student.getId());

        verify(studentRepo).findStudentById(ID);
        verify(modelMapper).map(student, StudentDto.class);


        assertEquals(fetchedStudent.getName(), student.getName());
        assertEquals(fetchedStudent.getEmail(), student.getEmail());
        assertEquals(fetchedStudent.getUniversity(), student.getUniversity());
        assertEquals(fetchedStudent.getPhone(), student.getPhone());
    }


}