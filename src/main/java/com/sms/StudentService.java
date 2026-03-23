package com.sms;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private static final String FILE_NAME = "studentData.bin";

    public void addStudent(Student student) {
        List<Student> students = getAllStudents();
        students.add(student);
        saveAllStudents(students);
    }

    public Student login(String username, String password) {
        return getAllStudents().stream()
                .filter(s -> s.getUsername().equalsIgnoreCase(username) && s.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public List<Student> getAllStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        List<Student> students = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Student s = (Student) ois.readObject();
                    students.add(s);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Log error
        }
        return students;
    }

    private void saveAllStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            for (Student s : students) {
                oos.writeObject(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean updateStudent(String oldUsername, Student updatedStudent) {
        List<Student> students = getAllStudents();
        boolean updated = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getUsername().equalsIgnoreCase(oldUsername)) {
                students.set(i, updatedStudent);
                updated = true;
                break;
            }
        }
        if (updated) saveAllStudents(students);
        return updated;
    }
}
