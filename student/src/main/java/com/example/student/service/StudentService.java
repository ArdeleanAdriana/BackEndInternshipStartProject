package com.example.student.service;

import com.example.student.dto.StudentDto;
import com.example.student.exception.UserNotFoundException;
import com.example.student.model.Student;
import com.example.student.repo.StudentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final ModelMapper mapper;

    @Autowired
    public StudentService(StudentRepo studentRepo, ModelMapper mapper) {
        this.studentRepo = studentRepo;
        this.mapper = mapper;
    }

    public StudentDto addStudent(StudentDto studentDto) {
        Student student = mapToEntity(studentDto);
        Student save = studentRepo.save(student);
        return mapToDTO(save);
    }

    public List<StudentDto> findAllStudents() {
        List<Student> students = studentRepo.findAll();
        return mapToDTOList(students);

    }

    public StudentDto updateStudent(StudentDto studentDto, Long id) {
//        Student student = mapToEntity(studentDto);
//        Student save = studentRepo.save(student);
//        return mapToDTO(student);
        Student student = studentRepo.findStudentById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setUniversity(studentDto.getUniversity());
        student.setPhone(studentDto.getPhone());
        Student save = studentRepo.save(student);
        return mapToDTO(student);
    }

    public StudentDto findStudentById(Long id) {
        Student student = studentRepo.findStudentById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
        return mapToDTO(student);
    }

    public void deleteStudent(Long id) {
        studentRepo.deleteStudentById(id);
    }

    public List<StudentDto> mapToDTOList(List<Student> students) {
        List<StudentDto> dtos = students.stream().map(student -> mapper.map(student, StudentDto.class)).collect(Collectors.toList());
        return dtos;
    }

    public StudentDto mapToDTO(Student student) {
        StudentDto dto = mapper.map(student, StudentDto.class);
        return dto;
    }

    public Student mapToEntity(StudentDto dto) {
        Student student = mapper.map(dto, Student.class);
        return student;
    }
}
