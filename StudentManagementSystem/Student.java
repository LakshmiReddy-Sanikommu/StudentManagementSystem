import java.io.*;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String section;
    private int id;

    public Student() {}

    public void getSData() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the username of the student: ");
        this.username = scanner.nextLine();

        System.out.print("Enter the password for the student: ");
        this.password = scanner.nextLine();

        System.out.print("Enter student section: ");
        this.section = scanner.nextLine();

        System.out.print("Enter ID of the student: ");
        this.id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSection() {
        return section;
    }

    public int getId() {
        return id;
    }

    public void display() {
        System.out.println("Username: " + username);
        System.out.println("Section: " + section);
        System.out.println("ID: " + id);
    }

    public void storeDataInFile() {
        try {
            File file = new File("studentData.bin");
            ObjectOutputStream oos;

            if (file.exists()) {
                oos = new AppendableObjectOutputStream(new FileOutputStream(file, true));
            } else {
                oos = new ObjectOutputStream(new FileOutputStream(file));
            }

            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchFromFile() {
        try {
            File file = new File("studentData.bin");
            if (!file.exists()) {
                System.out.println("No student data available.");
                return;
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your username: ");
            String inputUsername = scanner.nextLine();

            boolean found = false;
            while (true) {
                try {
                    Student student = (Student) ois.readObject();

                    if (student.getUsername().equalsIgnoreCase(inputUsername)) {
                        System.out.print("Enter your password: ");
                        String inputPassword = scanner.nextLine();

                        if (student.getPassword().equals(inputPassword)) {
                            System.out.println("Login successful!");
                            student.studentMenu();
                            found = true;
                            break;
                        } else {
                            System.out.println("Incorrect password.");
                            found = true;
                            break;
                        }
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            if (!found) {
                System.out.println("User not found.");
            }

            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromFile() {
        try {
            File file = new File("studentData.bin");
            if (!file.exists()) {
                System.out.println("No student data available.");
                return;
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                try {
                    Student student = (Student) ois.readObject();
                    student.display();
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changeStudentData(String usernameToUpdate) {
        try {
            File inputFile = new File("studentData.bin");
            File tempFile = new File("tempStudentData.bin");

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inputFile));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile));

            boolean updated = false;

            while (true) {
                try {
                    Student student = (Student) ois.readObject();

                    if (student.getUsername().equalsIgnoreCase(usernameToUpdate)) {
                        System.out.println("Enter new details for the student:");
                        student.getSData();
                        updated = true;
                    }

                    oos.writeObject(student);
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();
            oos.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            if (updated) {
                System.out.println("Student data updated successfully.");
            } else {
                System.out.println("No student found with the username: " + usernameToUpdate);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void studentMenu() {
        Scanner scanner = new Scanner(System.in);
        char choice;
        do {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View all assignments (For All)");
            System.out.println("2. View your assignments (For One)");
            System.out.println("3. Change username and password");
            System.out.println("4. Submit an assignment");
            System.out.println("5. See your details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.println("AssignmentForAll functionality not implemented.");
                    break;
                case 2:
                    System.out.println("AssignmentForOne functionality not implemented.");
                    break;
                case 3:
                    changeStudentData(this.username);
                    break;
                case 4:
                    System.out.println("SubmitAssignment functionality not implemented.");
                    break;
                case 5:
                    System.out.println("\nYour Details:");
                    display(); // Use the display method to show details
                    break;
                case 6:
                    System.out.println("Exiting Student Menu...");
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Try again.");
            }

            System.out.print("Do you want to continue? (y/n): ");
            choice = scanner.next().charAt(0);
            scanner.nextLine(); // Consume newline
        } while (choice == 'y' || choice == 'Y');
    }

    // Helper class to append objects in the file
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}
