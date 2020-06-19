package Tests;

import courses.Course;

import courses.CourseManager;
import courses.item.RegularItem;
import exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course course;
    RegularItem item1;
    RegularItem item2;

    @BeforeEach
    public void runBefore(){
        try {
            course = new Course("cpsc 210", "101", 10, "monday");
            item1 = new RegularItem("read", "9/10", false);
            item2 = new RegularItem("read", "8/10", false);
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
    }

    @Test
    public void addItemTest(){
        assertEquals(0, course.getItems().size());
        course.addItem(item1);
        assertEquals(1, course.getItems().size());
        assertEquals("cpsc 210", item1.getCourse().getName());
        course.addItem(item1);
        assertEquals(1, course.getItems().size());
        course.addItem(item2);
        assertEquals(2, course.getItems().size());
        assertEquals("cpsc 210", item2.getCourse().getName());

        RegularItem item3 = null;
        try {
            item3 = new RegularItem("watch pre-lecture videos", "8/10", false);
        }catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        course.addItem(item3);
        assertEquals(3, course.getItems().size());
        assertTrue(course.containsItem(item1));
        assertTrue(course.containsItem(item2));
    }

    @Test
    public void containsItemTest(){
        assertFalse(course.containsItem(item1));
        course.addItem(item1);
        assertTrue(course.containsItem(item1));
        assertFalse(course.containsItem(item2));
        course.addItem(item2);
        assertTrue(course.containsItem(item2));

        RegularItem item3 = null;
        try {
            item3 = new RegularItem("watch pre-lecture videos", "8/10", false);
        } catch (InvalidNameException e){
            System.out.println(e.presentException());
        }
        assertFalse(course.containsItem(item3));
    }

    @Test
    public void saveTest() {
        assertEquals(course.save(""), "");
        course.addItem(item1);
        assertEquals(course.save(""), "regular:read:9/10:false;");
        course.addItem(item2);
        assertEquals(course.save(""), "regular:read:9/10:false;regular:read:8/10:false;");
    }

    @Test
    public void toStringTest() {
        assertEquals("cpsc 210 |101 at 10 on monday", course.toString());
        Course otherCourse = null;
        try {
            otherCourse = new Course("arcl 108","L1a", 9, "tuesday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("arcl 108 |L1a at 9 on tuesday", otherCourse.toString());
    }

    @Test
    public void setCourseTest() {
        assertEquals("cpsc 210 |101 at 10 on monday", course.toString());
        try{
            course.setCourse("arcl 108", "L1a", 9, "tuesday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("arcl 108 |L1a at 9 on tuesday", course.toString());

        try{
            course.setCourse("a:rc;l 10,8", "L1a", 9, "tuesday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("arcl 108 |L1a at 9 on tuesday", course.toString());

        try{
            course.setCourse("arcl 108", "L;:1a,", 9, "tuesday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("arcl 108 |L1a at 9 on tuesday", course.toString());

        try{
            course.setCourse("arcl 108", "L1a,", 9, "t,u;e:sday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("arcl 108 |L1a at 9 on tuesday", course.toString());

        try{
            course.setCourse("arc;:,l 108", "L;:1a,", 9, "tuesday");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("arcl 108 |L1a at 9 on tuesday", course.toString());

        try{
            course.setCourse("arc;:,l 108", "L;:1a,", 9, "tu;e,s:day");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("arcl 108 |L1a at 9 on tuesday", course.toString());
    }

    @Test
    public void noPunctuationTest() {
        assertFalse(course.noPunctuation.noPunctuation(""));
        assertFalse(course.noPunctuation.noPunctuation("hi boys"));
        assertTrue(course.noPunctuation.noPunctuation("hi, boys"));
        assertTrue(course.noPunctuation.noPunctuation("hi; boys"));
        assertTrue(course.noPunctuation.noPunctuation("hi: boys"));
        assertTrue(course.noPunctuation.noPunctuation("hi, b:oy;s"));
    }

    @Test
    public void LoadItemsTest() {
        List<String> itemFields =
                course.splitter.splitBy("urgent:yes:9/10:false;regular:no:11/12:true;regular:hmm:8/11:false;",
                        ";");
        course.loadItems(itemFields);
        assertEquals(course.getItems().get(0).getDescription(), "yes");
        assertEquals(course.getItems().get(0).getType(), "urgent");
        assertFalse(course.getItems().get(0).getCompleted());
        assertEquals(course.getItems().get(0).getDueDate(), "9/10");

        assertEquals(course.getItems().get(1).getType(), "regular");
        assertEquals(course.getItems().get(1).getDescription(), "no");
        assertTrue(course.getItems().get(1).getCompleted());

        assertEquals(course.getItems().get(2).getDueDate(), "8/11");

    }

    @Test
    public void addUrgentItemTest() {
        course.addUrgentItem("a:", "b;", false);
        assertEquals(course.getItems().size(), 0);

        course.addRegularItem("a;", "c", false);
        assertEquals(course.getItems().size(), 0);
    }



    @Test
    public void removeItemTest() {
        course.addItem(item1);
        course.removeItem(item2);
        assertEquals(course.getItems().size(), 1);
        course.addItem(item2);
        assertEquals(course.getItems().size(), 2);
        course.removeItem(item1);
        assertNull(item1.getCourse());
        assertEquals(course.getItems().size(), 1);
    }

    @Test
    public void constructionTest() {
        try {
            course = new Course("cpsc 210", "1,0:1", 10, "monday");
        } catch (InvalidNameException e) {

        }

        try {
            course = new Course("cpsc 210", "101", 10, "mond;:a,y");
        } catch (InvalidNameException e) {

        }
        try {
            course = new Course("cp;;:sc 2,10", "101", 10,"monday");
        } catch (InvalidNameException e) {

        }
        try {
            course = new Course("cp;;:sc 2,10", ";10::;,1", 10,"mo;n,d:ay");
        } catch (InvalidNameException e) {

        }
        try {
            item1 = new RegularItem("r,e:a;d", "9/10", false);
        } catch (InvalidNameException e) {

        }
        try {
            item2 = new RegularItem("re:a,d", "8::,/1;0", false);
        } catch (InvalidNameException e) {

        }
        try {
            item2 = new RegularItem("read", "8::,/1;0", false);
        } catch (InvalidNameException e) {

        }
        assertEquals(course.getSection(), "101");
        assertEquals(course.getName(), "cpsc 210");
        assertEquals(item1.getDescription(), "read");
        assertEquals(item1.getDescription(), "read");
        assertEquals(item2.getDueDate(), "8/10");
    }

    @Test
    public void equalsTest() {
        Course course2 = null;
        Course course3 = null;
        Course course4 = null;
        Course course5 = null;
        Course course6 = null;
        try {
            course2 = new Course("cpsc 210", "100", 10, "monday");
            course3 = new Course("cpsc 210", "101", 9, "tuesday");
            course4 = new Course("", "", 10,"monday");
            course5 = new Course("cpsc 210", "101", 10, "monday");
            course6 = new Course("cpsc 210", "101", 10, "tuesday");

        } catch (InvalidNameException e){
        }

        assertNotEquals(course, course2);
        assertNotEquals(course, course3);
        assertNotEquals(course, course4);
        assertEquals(course, course);
        assertEquals(course, course5);
        assertNotEquals(course, course6);
        assertNotEquals(course, new CourseManager());
    }

    @Test
    public void gettersTest() {
        assertEquals(course.getDay(), "monday");
        assertEquals(course.getTime(), 10);
        assertEquals(course.hashCode(), -1357808667);
    }

}