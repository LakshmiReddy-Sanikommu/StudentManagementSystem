import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Lecturer");
            System.out.println("3. Admin");
            System.out.println("4. Add Student");
            System.out.println("5. Add Lecturer");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    Student student = new Student();
                    student.searchFromFile();
                    break;
                case 2:
                    Lecturer lecturer = new Lecturer();
                    lecturer.searchFromFile();
                    break;
                case 3:
                    System.out.println("Admin login not implemented yet.");
                    break;
                case 4:
                    Student newStudent = new Student();
                    newStudent.getSData();
                    newStudent.storeDataInFile();
                    System.out.println("Student added successfully.");
                    break;
                case 5:
                    Lecturer newLecturer = new Lecturer();
                    newLecturer.getData();
                    newLecturer.storeDataInFile();
                    System.out.println("Lecturer added successfully.");
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
