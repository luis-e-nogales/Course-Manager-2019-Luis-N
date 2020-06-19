package Tests;
import courses.CourseManager;
import courses.Course;

import exceptions.InvalidIndexException;
import exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CourseManagerTest {
    private CourseManager courseManager;
    private Course newCourse;
    private Course otherCourse;
    private Course thirdCourse;
    private Course fourthCourse;

    @BeforeEach
    public void runBefore(){
        courseManager = new CourseManager();

        try {
            newCourse = new Course("cpsc 210", "L1A", 9,"monday");
            otherCourse = new Course("cpsc 210", "L2A", 9, "tuesday");
            thirdCourse = new Course("ARCL 101", "101", 10, "monday");
            fourthCourse = new Course("ARCL 200", "103", 10, "monday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        try {
            Course failCourse = new Course("cpsc ,210", ";L1A", 9, "f;;j");
        } catch (InvalidNameException e) {
            System.out.println("caught the exception");
        }

    }

    @Test
    public void addCourseTest() throws InvalidNameException {
        Course badCourse = new Course("Cpsc 221", "101", 9,"moonday");
        assertEquals("invalid day", courseManager.addCourse(badCourse));
        assertEquals(0, courseManager.getCourses().size());
        courseManager.addCourse(newCourse);
        assertTrue(courseManager.getItemMap().containsKey(newCourse));
        assertEquals(1, courseManager.getCoursesOnDay(newCourse.getDay()).size());
        courseManager.addCourse(newCourse);
        assertEquals(1, courseManager.getCoursesOnDay(newCourse.getDay()).size());
        courseManager.addCourse(otherCourse);
        assertEquals(1, courseManager.getCoursesOnDay(otherCourse.getDay()).size());

        courseManager.addCourse(thirdCourse);
        assertEquals("there is already a course at this time on that day",
                courseManager.addCourse(fourthCourse));
    }

    @Test
    public void containsCourseTest() {
        assertFalse(courseManager.containsCourse(newCourse, courseManager.getCourses()));
        courseManager.addCourse(newCourse);
        assertTrue(courseManager.containsCourse(newCourse, courseManager.getCoursesOnDay(newCourse.getDay())));
        assertFalse(courseManager.containsCourse(otherCourse, courseManager.getCoursesOnDay(otherCourse.getDay())));
        courseManager.addCourse(otherCourse);
        courseManager.addCourse(thirdCourse);
        assertTrue(courseManager.containsCourse(thirdCourse, courseManager.getCoursesOnDay(thirdCourse.getDay())));

        Course fourthCourse = null;
        Course fifthCourse = null;
        try {
            fourthCourse = new Course("GEOB 200", "101", 9, "monday");
            fifthCourse = new Course("cpsc 210", "L2A", 11, "monday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
        assertFalse(courseManager.containsCourse(fourthCourse, courseManager.getCoursesOnDay(fourthCourse.getDay())));
        assertFalse(courseManager.containsCourse(fifthCourse, courseManager.getCoursesOnDay(fifthCourse.getDay())));
    }

    @Test
    public void presentCourseListTest() {
        assertEquals("", courseManager.presentCourseList(courseManager.getCourses()));
        courseManager.addCourse(newCourse);
        assertEquals("[0]cpsc 210 "
                , courseManager.presentCourseList(courseManager.getCoursesOnDay(newCourse.getDay())));
        courseManager.addCourse(thirdCourse);
        assertEquals("[0]cpsc 210 [1]ARCL 101 "
                , courseManager.presentCourseList(courseManager.getCoursesOnDay(newCourse.getDay())));
        courseManager.addCourse(otherCourse);
        assertEquals("[0]cpsc 210 "
                , courseManager.presentCourseList(courseManager.getCoursesOnDay(otherCourse.getDay())));
    }

    @Test
    public void removeCourseTest() {
        assertEquals("invalid course index", courseManager.removeCourse(0, "monday"));
        assertEquals("invalid course index", courseManager.removeCourse(-1 , "monday"));
        courseManager.addCourse(newCourse);
        assertTrue(courseManager.getItemMap().containsKey(newCourse));
        assertEquals(1, courseManager.getCoursesOnDay(newCourse.getDay()).size());
        assertEquals("invalid course index", courseManager.removeCourse(1, newCourse.getDay()));
        assertEquals("removed course", courseManager.removeCourse(0, newCourse.getDay()));
        assertFalse(courseManager.getItemMap().containsKey(newCourse));
        assertEquals(0, courseManager.getCoursesOnDay(newCourse.getDay()).size());
        courseManager.addCourse(newCourse);
        courseManager.addCourse(otherCourse);
        courseManager.addCourse(thirdCourse);
        assertEquals(2, courseManager.getCoursesOnDay(newCourse.getDay()).size());
        assertEquals(1, courseManager.getCoursesOnDay(otherCourse.getDay()).size());
        assertEquals("removed course", courseManager.removeCourse(1, thirdCourse.getDay()));
        assertFalse(courseManager.getItemMap().containsKey(thirdCourse));
        assertTrue(courseManager.getItemMap().containsKey(otherCourse));
        assertEquals("removed course", courseManager.removeCourse(0, otherCourse.getDay()));
        assertEquals(1, courseManager.getCoursesOnDay(newCourse.getDay()).size());
        assertFalse(courseManager.getItemMap().containsKey(otherCourse));
    }

    @Test
    public void splitByTest() {
        assertEquals(courseManager.splitter.splitBy("", " ").size(), 1);
        assertEquals(courseManager.splitter.splitBy("", " ").get(0), "");

        assertEquals(courseManager.splitter.splitBy("hello I am", " ").size(), 3);
        assertEquals(courseManager.splitter.splitBy("hello I am", " ").get(2), "am");

        assertEquals(courseManager.splitter.splitBy("I love Comp-sci, but tests :(", ",").size(), 2);
    }

    @Test
    public void saveTest() throws IOException {
        courseManager.save("SaveTest.txt");
        courseManager.load("SaveTest.txt");
        assertEquals(courseManager.getCourses().size(), 0);
        courseManager.addCourse(newCourse);
        courseManager.save("SaveTest.txt");
        //courseManager.removeCourse(0, newCourse.getDay());
        courseManager.load("SaveTest.txt");
        assertEquals(courseManager.getCoursesOnDay(newCourse.getDay()).size(), 1);
        courseManager.addCourse(otherCourse);
        courseManager.addCourse(thirdCourse);
        courseManager.save("SaveTest.txt");
        courseManager.load("SaveTest.txt");
        assertEquals(courseManager.getCoursesOnDay("monday").size(), 2);
        assertEquals(courseManager.getCoursesOnDay("tuesday").size(), 1);
        assertEquals(courseManager.getCoursesOnDay("monday").get(1).getName(), "ARCL 101");
    }


    @Test
    public void presentExceptionTest() {
        InvalidIndexException indexException = new InvalidIndexException();
        InvalidNameException nameException = new InvalidNameException();

        assertEquals(indexException.presentException(), "invalid index");
        assertEquals(nameException.presentException(), "string contained ; or , or :");
    }

    @Test
    public void LoadTest() throws IOException {
        //ILLEGAL TEST DUE TO FILE NOT FOUND
        try {
            courseManager.load("notARealFile.txt");
        } catch (IOException e) {
            System.out.println("caught exception");
        }

        assertEquals(courseManager.getCourses().size(), 0);

        // ILLEGAL TEST Due to punctuation
        courseManager.load("IncorrectLoad.txt");
        assertEquals(courseManager.getCourses().size(), 0);

        //Initial load test with a mix of courses with and without items to do
        courseManager.load("LoadTest.txt");
        CourseManager courseManagerStart = new CourseManager();
        courseManagerStart.load("LoadTest.txt");
        assertEquals(courseManager.getCoursesOnDay("wednesday").size(), 2);
        assertEquals(courseManager.getCoursesOnDay("tuesday").size(), 1);
        assertEquals(courseManager.getCoursesOnDay("monday").size(), 1);
        assertEquals(courseManager.getCoursesOnDay("monday").get(0).getName(), "cpsc 210");
        assertEquals(courseManager.getCoursesOnDay("wednesday").get(1).getSection(), "L1B");
        assertEquals(courseManager.getCoursesOnDay("tuesday").get(0).getTime(), 30);
        assertEquals("prepare for quiz"
                , courseManager.getCoursesOnDay("wednesday").get(1).getItems().get(0).getDescription());
        assertEquals(courseManager.getCoursesOnDay("wednesday").get(1).getItems().get(1).getDescription(),
                "do deliverable");

       assertEquals(courseManager.getCoursesOnDay("monday").get(0).getItems().get(0).getType(),
               "regular");
        assertEquals(courseManager.getCoursesOnDay("monday").get(0).getItems().get(1).getType(),
                "urgent");

        assertEquals(courseManager.getCoursesOnDay("tuesday").get(0).getName(), "math 200");
        assertEquals(courseManager.getCoursesOnDay("tuesday").get(0).getItems().size(), 0);

        //empty case
        courseManager.removeAllCourses();
        courseManager.save("LoadTest.txt");
        courseManager.load("LoadTest.txt");
        assertEquals(courseManager.getCoursesOnDay("monday").size(), 0);
        assertEquals(courseManager.getCoursesOnDay("tuesday").size(), 0);
        assertEquals(courseManager.getCoursesOnDay("wednesday").size(), 0);
        assertEquals(courseManager.getCoursesOnDay("thursday").size(), 0);
        assertEquals(courseManager.getCoursesOnDay("friday").size(), 0);

        //case with no items throughout all list
        courseManager.addCourse(newCourse);
        courseManager.addCourse(otherCourse);
        courseManager.save("LoadTest.txt");
        courseManager.load("LoadTest.txt");
        assertEquals(courseManager.getCoursesOnDay("monday").get(0).getItems().size(), 0);
        assertEquals(courseManager.getCoursesOnDay("monday").get(0).getName(), "cpsc 210");

        courseManagerStart.save("LoadTest.txt");

    }

    @Test
    public void removeCourseTest2() {
        assertEquals("couldn't find course", courseManager.removeCourse(newCourse));
        courseManager.addCourse(newCourse);
        assertEquals("couldn't find course", courseManager.removeCourse(otherCourse));
        assertEquals("removed course", courseManager.removeCourse(newCourse));
        courseManager.printAllCourses();
        courseManager.getCoursesMap();
    }

}
