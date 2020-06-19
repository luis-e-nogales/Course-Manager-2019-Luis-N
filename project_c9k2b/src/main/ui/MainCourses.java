package ui;

import courses.Course;
import courses.CourseManager;
import exceptions.InvalidIndexException;
import exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.Scanner;

public class MainCourses {
    Scanner scanner;
    CourseManager manager;
    MainItems mainItems;

    public MainCourses(Scanner scanner, CourseManager manager) {
        this.scanner = scanner;
        this.manager = manager;
    }

    //EFFECTS: creates a course through user inputs
    public Course createCourse() throws InvalidNameException {
        System.out.println("Input Course name: ");
        String courseName = scanner.nextLine();
        System.out.println("Input Course section: ");
        String courseSection = scanner.nextLine();
        System.out.println("Input Course time: ");
        int courseTime = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Input Course day: ");
        String  courseDay = scanner.nextLine();

        return (new Course(courseName, courseSection, courseTime, courseDay));
    }

    //Requires: action to be "remove" or "view/edit"
    //Modifies: this
    //Effects: prompts the user to select which course should a particular action be applied to.
    public String selectCourseIndexSetupDay(String action) {
        System.out.println("on which day? (monday/tuesday/wednesday/thursday/friday)");
        String day = scanner.nextLine().toLowerCase();
        return selectIndexSetup(action, day);
    }

    private String selectIndexSetup(String action, String day) {
        if (manager.getCoursesOnDay(day).size() < 1) {
            return "There are no courses in the list";
        } else {
            System.out.println("which course would you like to " + action + "?");
            int courseIndex;
            try {
                courseIndex = selectCourseIndex(manager.getCoursesOnDay(day));
            } catch (InvalidIndexException e) {
                return e.presentException();
            }
            if (action.equals("remove")) {
                return manager.removeCourse(courseIndex, day);
            } else if (action.equals("view/edit")) {
                return editCourses(manager.getCoursesOnDay(day).get(courseIndex), mainItems);
            }
            return "";
        }
    }

    //EFFECTS: presents a course, and asks the user to select an index
    private int selectCourseIndex(ArrayList<Course> listOfCourses) {
        System.out.println(manager.presentCourseList(listOfCourses));
        int courseIndex = scanner.nextInt();
        scanner.nextLine();

        if (courseIndex >= listOfCourses.size() || courseIndex < 0) {
            throw new InvalidIndexException();
        }
        return  courseIndex;
    }

    //MODIFIES: this
    //EFFECTS: through user input decides whether to view or edit courses
    private String editCourses(Course course, MainItems mainItems) {
        System.out.println("Would you like to: view items, add item");
        String nextCommand = scanner.nextLine();
        if (nextCommand.equals("view items")) {
            System.out.println(course.getItems());
            return "presented items for " + course.getName();
        } else if (nextCommand.equals("add item")) {
            mainItems.addItem(course);
            return "added item to " + course.getName();
        }
        return "invalid input";
    }
}
