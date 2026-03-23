import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Lecturer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private int lecturerId;

    public Lecturer() {}

    public void getData() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        this.username = scanner.nextLine();

        System.out.print("Enter your password: ");
        this.password = scanner.nextLine();

        System.out.print("Enter your lecturer ID: ");
        this.lecturerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    }

    public void display() {
        System.out.println("Username: " + username);
    }

    public void storeDataInFile() {
        try {
            File file = new File("lecturerData.bin");
            ArrayList<Lecturer> lecturers = new ArrayList<>();
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    lecturers = (ArrayList<Lecturer>) ois.readObject();
                }
            }
            lecturers.add(this);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(lecturers);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void searchFromFile() {
        try {
            File file = new File("lecturerData.bin");
            if (!file.exists()) {
                System.out.println("No lecturer data available.");
                return;
            }

            ArrayList<Lecturer> lecturers;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                lecturers = (ArrayList<Lecturer>) ois.readObject();
            }

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your username: ");
            String inputUsername = scanner.nextLine();

            for (Lecturer lecturer : lecturers) {
                if (lecturer.username.equals(inputUsername)) {
                    System.out.print("Enter your password: ");
                    String inputPassword = scanner.nextLine();

                    if (lecturer.password.equals(inputPassword)) {
                        System.out.println("Login successful!");
                        lecturerMenu();
                        return;
                    } else {
                        System.out.println("Incorrect password.");
                        return;
                    }
                }
            }

            System.out.println("User not found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lecturerMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Assign assignment to all");
            System.out.println("2. Assign assignment to one");
            System.out.println("3. Change your password");
            System.out.println("4. Change assignment for all");
            System.out.println("5. Change assignment for one");
            System.out.println("6. Delete assignment for all");
            System.out.println("7. Delete assignment for one");
            System.out.println("8. Check assignments submitted by students");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    AssignmentForAll.assignToAll();
                    break;
                case 2:
                    AssignmentForOne.assignToOne();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    AssignmentForAll.changeAssignmentForAll();
                    break;
                case 5:
                    AssignmentForOne.changeAssignmentForOne();
                    break;
                case 6:
                    AssignmentForAll.deleteAssignmentForAll();
                    break;
                case 7:
                    AssignmentForOne.deleteAssignmentForOne();
                    break;
                case 8:
                    SubmitAssignment.checkSubmittedAssignments();
                    break;
                case 9:
                    System.out.println("Exiting menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 9);
    }

    private void changePassword() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();

        if (!this.password.equals(currentPassword)) {
            System.out.println("Incorrect current password.");
            return;
        }

        System.out.print("Enter your new password: ");
        this.password = scanner.nextLine();

        System.out.println("Password updated successfully.");
    }
}