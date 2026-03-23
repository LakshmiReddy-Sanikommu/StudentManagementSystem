package com.sms;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LecturerService {
    private static final String FILE_NAME = "lecturerData.bin";

    public void addLecturer(Lecturer lecturer) {
        List<Lecturer> lecturers = getAllLecturers();
        lecturers.add(lecturer);
        saveAllLecturers(lecturers);
    }

    public Lecturer login(String username, String password) {
        return getAllLecturers().stream()
                .filter(l -> l.getUsername().equalsIgnoreCase(username) && l.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public List<Lecturer> getAllLecturers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        List<Lecturer> lecturers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lecturers = (List<Lecturer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lecturers;
    }

    private void saveAllLecturers(List<Lecturer> lecturers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(lecturers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
