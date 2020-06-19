package ui;

import courses.Course;

import java.util.Scanner;

public class MainItems {

    private Scanner scanner;

    public MainItems(Scanner scanner) {
        this.scanner = scanner;
    }

    //MODIFIES: this
    //EFFECTS: handles user interaction for adding items
    public void addItem(Course course) {
        System.out.println("type out item description");
        String itemName = scanner.nextLine();
        System.out.println("type out item date (D/M)");
        String itemDate = scanner.nextLine();
        System.out.println("is the item urgent (y/n)?");
        String urgency = scanner.nextLine();

        if (urgency.equals("y")) {
            course.addUrgentItem(itemName, itemDate, false);
        } else {
            course.addRegularItem(itemName, itemDate, false);
        }
    }
}
