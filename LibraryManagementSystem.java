import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Book {
    int id;
    String name;
    boolean isIssued;

    Book(int id, String name, boolean isIssued) {
        this.id = id;
        this.name = name;
        this.isIssued = isIssued;
    }
}

public class LibraryManagementSystem {

    static ArrayList<Book> books = new ArrayList<>();
    static final String FILE_NAME = "books.txt";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Always generate fresh 500 books 
        books.clear();
        generateDummyBooks();
        saveBooks();

        int choice;

        while (true) {
            System.out.println("\n========= LIBRARY MANAGEMENT SYSTEM =========");
            System.out.println("1. View Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewBooks();
                    break;

                case 2:
                    System.out.print("Enter Book ID to issue: ");
                    int issueId = sc.nextInt();
                    issueBook(issueId);
                    break;

                case 3:
                    System.out.print("Enter Book ID to return: ");
                    int returnId = sc.nextInt();
                    returnBook(returnId);
                    break;

                case 4:
                    System.out.println("Exiting... Data saved.");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Generate 500 books
    static void generateDummyBooks() {
        String[] names = {
            "Java Basics", "Data Structures", "Algorithms", "Operating Systems",
            "Computer Networks", "DBMS", "Machine Learning", "AI Basics",
            "Python Guide", "C++ Programming", "Web Development", "Cyber Security"
        };

        for (int i = 1; i <= 500; i++) {
            String name = names[i % names.length] + " " + i;
            books.add(new Book(i, name, false));
        }
    }

    // View books (TABLE FORMAT )
    static void viewBooks() {
        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s\n", "ID", "BOOK NAME", "STATUS");
        System.out.println("--------------------------------------------------------------");

        for (Book b : books) {
            System.out.printf("%-10d %-30s %-15s\n",
                    b.id,
                    b.name,
                    (b.isIssued ? "Issued" : "Available"));
        }

        System.out.println("--------------------------------------------------------------");
    }

    // Issue book
    static void issueBook(int id) {
        for (Book b : books) {
            if (b.id == id) {
                if (!b.isIssued) {
                    b.isIssued = true;
                    saveBooks();
                    System.out.println(" Book issued successfully!");
                } else {
                    System.out.println(" Already issued!");
                }
                return;
            }
        }
        System.out.println(" Book not found!");
    }

    // Return book
    static void returnBook(int id) {
        for (Book b : books) {
            if (b.id == id) {
                if (b.isIssued) {
                    b.isIssued = false;
                    saveBooks();
                    System.out.println(" Book returned successfully!");
                } else {
                    System.out.println(" Book was not issued!");
                }
                return;
            }
        }
        System.out.println(" Book not found!");
    }

    // Save to file
    static void saveBooks() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));

            for (Book b : books) {
                writer.write(b.id + "," + b.name + "," + b.isIssued);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }
}