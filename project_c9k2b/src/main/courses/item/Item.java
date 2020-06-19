package courses.item;

import courses.Course;
import courses.CourseManager;
import courses.NoPunctuation;
import courses.Saveable;
import exceptions.InvalidNameException;

public abstract class Item {
    protected String description;
    protected String dueDate;
    protected boolean completed;
    protected String type;
    protected Course course;
    private NoPunctuation noPunctuation = new NoPunctuation();

    //REQUIRES: Due date must be written as D/M. Description,  and duedate can't have any (; or , or :) in the String
    public Item(String description, String dueDate, boolean completed) throws InvalidNameException {
        if (noPunctuation.noPunctuation(description) || noPunctuation.noPunctuation(dueDate)) {
            throw new InvalidNameException();
        }
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.type = "";
    }

    //Requires: Due date must be written as D/M, and cant have any (; or , or :) in the String
    //Modifies: this
    //Effects: changes the dueDate
    public void setDueDate(String newDueDate) throws InvalidNameException {
        if (noPunctuation.noPunctuation(newDueDate)) {
            throw new InvalidNameException();
        }
        dueDate = newDueDate;
    }

    //Effects: gets the dueDate
    public String getDueDate() {
        return dueDate;
    }

    //Effects: gets the dueDate
    public String getDescription() {
        return description;
    }

    //Requires: description can't have any (; or , or :) characters in the String
    //MODIFIES: this
    //EFFECTS: sets the description to something else
    public void setDescription(String newDescription) throws InvalidNameException {
        if (noPunctuation.noPunctuation(newDescription)) {
            throw new InvalidNameException();
        }
        description = newDescription;
    }

    //MODIFIES: this
    //EFFECTS: changes states of completion
    public void changeCompleted() {
        if (completed) {
            completed = false;
        } else {
            completed = true;
        }
    }

    //EFFECTS: get completed status
    public boolean getCompleted() {
        return completed;
    }

    //EFFECTS: gets type
    public abstract String getType();

    //EFFECTS: returns the appropriate format for printing an item
    public String toString() {
        return description + " due on " + dueDate;
    }

    //Effects: formats the fields of an item
    public String save() {
        return type + ":" + description + ":" + dueDate + ":" + completed;
    }

    //REQUIRES: Course can't have been set to another course before
    //MODIFIES: this
    //EFFECTS: sets course that contains this item
    public void setCourse(Course c) {
        course = c;
        if (!c.containsItem(this)) {
            c.addItem(this);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the course for this item to null
    public void removeCourse(Course c) {
        course = null;
        if (c.containsItem(this)) {
            c.removeItem(this);
        }
    }

    //EFFECTS: returns course
    public Course getCourse() {
        return course;
    }

}
