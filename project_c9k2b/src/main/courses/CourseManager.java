package courses;

import courses.item.*;
import exceptions.InvalidNameException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import weather.WeatherHandler;


public class CourseManager implements Saveable, Loadable {

    private ArrayList<Course> courses;
    private HashMap<Course, ArrayList<Item>> itemMap;
    private HashMap<String, ArrayList<Course>> coursesMap;
    public Splitter splitter = new Splitter();
    private WeatherHandler weatherHandler;

    public CourseManager() {
        courses = new ArrayList<>();
        itemMap = new HashMap<>();
        coursesMap = new HashMap<>();
        coursesMap.put("monday", new ArrayList<>());
        coursesMap.put("tuesday", new ArrayList<>());
        coursesMap.put("wednesday", new ArrayList<>());
        coursesMap.put("thursday", new ArrayList<>());
        coursesMap.put("friday", new ArrayList<>());
    }

    //EFFECTS: determines whether a course description matches a course in the list.
    public boolean containsCourse(Course course1, ArrayList<Course> listOfCourses) {
        String name = course1.getName();
        String section = course1.getSection();
        int time = course1.getTime();
        for (Course course: listOfCourses) {
            if (course.getName().equals(name) && course.getSection().equals(section) && course.getTime() == time) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns the courses
    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    //MODIFIES: this
    //EFFECTS: adds a Course to the course array list if not already added, and returns the action that happened
    public String addCourse(Course addedCourse) {
        ArrayList<Course> courseArrayList = coursesMap.get(addedCourse.getDay());

        if (courseArrayList == null) {
            return "invalid day";
        }
        if (this.containsCourse(addedCourse, courseArrayList)) {
            return "course already in list";
        }

        for (Course c: courseArrayList) {
            if (c.getTime() == addedCourse.getTime()) {
                return "there is already a course at this time on that day";
            }
        }
        courseArrayList.add(addedCourse);
        itemMap.put(addedCourse, addedCourse.getItems());

        return "added course";
    }

    //Effects: Takes a list of courses and presents the course name with the index next to it
    public String presentCourseList(ArrayList<Course> listOfCourses) {
        String currentCourses = "";
        for (int i = 0;  i < listOfCourses.size(); i++) {
            currentCourses = currentCourses + "[" + i + "]" + listOfCourses.get(i).getName() + " ";
        }
        return currentCourses;
    }

    //MODIFIES: this
    //EFFECTS: removes a course at an index from the courses array and prints it removed a course or invalid input
    public String removeCourse(int courseIndex, String day) {
        try {
            Course c = getCoursesOnDay(day).get(courseIndex);
            getCoursesOnDay(day).remove(courseIndex);
            itemMap.remove(c);
        } catch (IndexOutOfBoundsException e) {
            return "invalid course index";
        }
        return "removed course";
    }

    //MODIFIES: this
    //EFFECTS: removes a course if it is found
    public String removeCourse(Course course) {
        int courseIndex = -1;
        for (int i = 0; i < coursesMap.get(course.getDay()).size(); i++) {
            if (course.equals(coursesMap.get(course.getDay()).get(i))) {
                courseIndex = i;
            }
        }

        if (courseIndex != -1) {
            coursesMap.get(course.getDay()).remove(courseIndex);
            return "removed course";
        } else {
            return "couldn't find course";
        }

    }

    //Modifies: this
    //Effects: loads information from a text file about a CourseManger onto the program.
    @Override
    public void load(String fileName) throws IOException {
        removeAllCourses();
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitter.splitBy(line, ",");
            Course currentCourse;
            try {
                currentCourse = new Course(partsOfLine.get(0),
                        partsOfLine.get(1), Integer.parseInt(partsOfLine.get(2)), partsOfLine.get(3));
            } catch (InvalidNameException e) {
                System.out.println(e.presentException());
                continue;
            }

            if (!(partsOfLine.size() - 1 == 3)) {
                List<String> itemsToDo = splitter.splitBy(partsOfLine.get(partsOfLine.size() - 1), ";");
                currentCourse.loadItems(itemsToDo);
            }
            addCourse(currentCourse);
        }
    }

    //Modifies: text file
    //Effects: saves information onto a text file
    @Override
    public String save(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter cleaner = new PrintWriter(fileName,"UTF-8");
        cleaner.close();
        PrintWriter writer = new PrintWriter(fileName,"UTF-8");

        for (String key: coursesMap.keySet()) {
            for (Course course: coursesMap.get(key)) {
                String line = course.getName() + "," + course.getSection() + "," + course.getTime() + ","
                        + course.getDay() + "," + course.save("");
                writer.println(line);
            }
        }

//        for (Course course: this.courses) {
//            String line = course.getName() + "," + course.getSection() + "," + course.getTime() + ","
//                    + course.getDay() + "," + course.save("");
//            writer.println(line);
//        }
        writer.close();
        return "Saved file";
    }

    //EFFECTS: returns the map of all the items
    public HashMap<Course, ArrayList<Item>> getItemMap() {
        return itemMap;
    }


    //EFFECTS: returns the courses on a given day
    public ArrayList<Course> getCoursesOnDay(String day) {
        return coursesMap.get(day);
    }

    //EFFECTS: returns the courses map
    public HashMap<String, ArrayList<Course>> getCoursesMap() {
        return coursesMap;
    }

    //EFFECTS: clears the whole set of courses
    public void removeAllCourses() {
        for (String key: coursesMap.keySet()) {
            coursesMap.get(key).clear();
        }
    }

    //Effects: prints all the courses in the manager
    public void printAllCourses() {
        for (String key: coursesMap.keySet()) {
            System.out.println(coursesMap.get(key));
        }
    }

}
