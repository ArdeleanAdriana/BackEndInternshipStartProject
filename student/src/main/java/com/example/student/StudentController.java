package com.example.student;

import com.example.student.dto.StudentDto;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public ResponseEntity<List<StudentDto>>  getAllStudents(){
        List<StudentDto> students = studentService.findAllStudents();
        return  new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto>  getStudentById(@PathVariable("id") Long id){
        StudentDto studentDto = studentService.findStudentById(id);
        return  new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<StudentDto> addStudent(@RequestBody @Valid StudentDto dto){
        StudentDto newStudent = studentService.addStudent(dto);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<StudentDto> updateStudent(@RequestBody @Valid StudentDto studentDto){
        StudentDto updateStudent = studentService.updateStudent(studentDto);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
