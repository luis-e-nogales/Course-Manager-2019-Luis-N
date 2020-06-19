package ui.gui;

import courses.Course;

import javax.swing.*;
import java.util.Objects;

public class CourseButton extends JButton {
    private Course buttonCourse;

    public CourseButton(String text, Course course) {
        super(text);
        buttonCourse = course;
    }

    //EFFECTS: gets the course associated with button
    public Course getCourse() {
        return  buttonCourse;
    }


    //Effects: determines if two course buttons are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseButton)) {
            return false;
        }
        CourseButton that = (CourseButton) o;
        return Objects.equals(buttonCourse, that.buttonCourse);
    }

    //Effects: gets the hashCode
    @Override
    public int hashCode() {
        return Objects.hash(buttonCourse);
    }
}
