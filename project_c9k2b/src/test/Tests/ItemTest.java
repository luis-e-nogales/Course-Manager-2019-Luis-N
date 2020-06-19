package Tests;
import courses.Course;
import courses.item.*;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class ItemTest {
    Item item;
    Item urgentItem;
    Course c1;
    Course c2;

    @BeforeEach
    public void runBefore() {
        try {
            item = new RegularItem("do webwork", "9/10", false);
            urgentItem = new UrgentItem("finish lab", "3/10", false);
            c1 = new Course("math 200", "100", 9, "monday");
            c2 = new Course("cpsc 221", "101", 10, "monday");

        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
    }

    @Test
    public void setDueDateTest() {
        assertEquals("9/10", item.getDueDate());
        try {
            item.setDueDate("11/10");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
        assertEquals("11/10", item.getDueDate());

        try {
            item.setDueDate("1;2/,1:0");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
        assertEquals("11/10", item.getDueDate());
    }

    @Test
    public void setDescriptionTest() {
        assertEquals("do webwork", item.getDescription());
        try {
            item.setDescription("read pre-lab");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }

        assertEquals("read pre-lab", item.getDescription());

        try {
            item.setDescription("d;o p:r,e-lab");
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
        assertEquals("read pre-lab", item.getDescription());
    }

    @Test
    public void changeCompletedTest() {
        assertFalse(item.getCompleted());
        item.changeCompleted();
        assertTrue(item.getCompleted());
        item.changeCompleted();
        assertFalse(item.getCompleted());
    }

    @Test
    public void getTypeTest() {
        assertEquals("regular", item.getType());
        assertEquals("urgent", urgentItem.getType());
    }

    @Test
    public void saveTest() {
        assertEquals("regular:do webwork:9/10:false" ,item.save());
        assertEquals("urgent:finish lab:3/10:false" ,urgentItem.save());

    }

    @Test
    public void setCourseTest() {
        assertNull(item.getCourse());
        assertEquals(c1.getItems().size() , 0);
        item.setCourse(c1);
        assertEquals(c1.getItems().size() , 1);
        assertEquals(item.getCourse().getName(), "math 200");
        item.setCourse(c1);
        assertEquals(c1.getItems().size() , 1);
        assertEquals(item.getCourse().getName(), "math 200");
    }

    @Test
    public void removeCourseTest(){
        item.setCourse(c1);
        assertEquals(c1.getItems().size() , 1);
        assertEquals(item.getCourse().getName(), "math 200");
        item.removeCourse(c1);
        assertNull(item.getCourse());
        assertEquals(c1.getItems().size() , 0);
        item.removeCourse(c1);
        assertNull(item.getCourse());
        assertEquals(c1.getItems().size() , 0);
    }

    //Ye push this
    @Test
    public void toStringTest() throws InvalidNameException{
        assertEquals(item.toString(), "(false)do webwork due on 9/10");

        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now();
        dueDate = dueDate.plusDays(9);

        String dueDateString = Integer.toString(dueDate.getDayOfMonth())
                + "/" + Integer.toString(dueDate.getMonthValue());

        urgentItem.setDueDate(dueDateString);

        assertEquals(urgentItem.toString(), "(false)finish lab due on " + dueDateString + " *DUE SOON*");

        dueDate = dueDate.plusDays(1);

        dueDateString = Integer.toString(dueDate.getDayOfMonth())
                + "/" + Integer.toString(dueDate.getMonthValue());

        urgentItem.setDueDate(dueDateString);
        assertEquals(urgentItem.toString(), "(false)finish lab due on " + dueDateString + " *DUE SOON*");

        dueDate = dueDate.plusDays(1);

        dueDateString = Integer.toString(dueDate.getDayOfMonth())
                + "/" + Integer.toString(dueDate.getMonthValue());

        urgentItem.setDueDate(dueDateString);
        assertEquals(urgentItem.toString(), "(false)finish lab due on " + dueDateString);

        dueDate = dueDate.minusDays(20);

        dueDateString = Integer.toString(dueDate.getDayOfMonth())
                + "/" + Integer.toString(dueDate.getMonthValue());
        urgentItem.setDueDate(dueDateString);
        assertEquals(urgentItem.toString(), "(false)finish lab due on " + dueDateString);
    }
}
