package com.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/student/login")
    public Student studentLogin(@RequestBody Map<String, String> creds) {
        Student s = studentService.login(creds.get("username"), creds.get("password"));
        if (s == null) throw new RuntimeException("Invalid credentials");
        return s;
    }

    @PostMapping("/lecturer/login")
    public Lecturer lecturerLogin(@RequestBody Map<String, String> creds) {
        Lecturer l = lecturerService.login(creds.get("username"), creds.get("password"));
        if (l == null) throw new RuntimeException("Invalid credentials");
        return l;
    }

    @PostMapping("/student/add")
    public String addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return "Student added successfully";
    }

    @PostMapping("/lecturer/add")
    public String addLecturer(@RequestBody Lecturer lecturer) {
        lecturerService.addLecturer(lecturer);
        return "Lecturer added successfully";
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }
    
    @PutMapping("/student/{username}")
    public String updateStudent(@PathVariable String username, @RequestBody Student student) {
        boolean updated = studentService.updateStudent(username, student);
        if (!updated) throw new RuntimeException("Student not found");
        return "Student updated";
    }

    @PostMapping("/assignment/{action}")
    public String assignmentAction(@PathVariable String action) {
        return switch(action) {
            case "assignAll" -> assignmentService.assignToAll();
            case "assignOne" -> assignmentService.assignToOne();
            case "changeAll" -> assignmentService.changeAssignmentForAll();
            case "changeOne" -> assignmentService.changeAssignmentForOne();
            case "deleteAll" -> assignmentService.deleteAssignmentForAll();
            case "deleteOne" -> assignmentService.deleteAssignmentForOne();
            case "check" -> assignmentService.checkSubmittedAssignments();
            default -> "Invalid action";
        };
    }
}
