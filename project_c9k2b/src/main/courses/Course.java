package courses;

import courses.item.Item;
import courses.item.RegularItem;
import courses.item.UrgentItem;
import exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Saveable {
    //A course with its own information
    private String department;
    private String courseName;
    private String section;
    private String day;
    private int hour;
    private ArrayList<Item> itemsToDo;
    public NoPunctuation noPunctuation = new NoPunctuation();
    public Splitter splitter = new Splitter();


    //Requires: no commas, semicolons, or colons in courseName or Section or course time
    public Course(String courseName, String courseSection, int courseTime, String day) throws InvalidNameException {
        if (noPunctuation.noPunctuation(courseName)
                || noPunctuation.noPunctuation(courseSection) ||  noPunctuation.noPunctuation(day))  {
            throw new InvalidNameException();
        }
        this.courseName = courseName;
        this.section = courseSection;
        this.hour = courseTime;
        this.day = day;
        this.itemsToDo = new ArrayList<>();
    }

    //EFFECTS: returns course name
    public String getName() {
        return this.courseName;
    }

    //EFFECTS: returns course section
    public String getSection() {
        return this.section;
    }

    //EFFECTS: returns course time
    public int getTime() {
        return this.hour;
    }

    //MODIFIES: this
    //EFFECTS: changes the name, section, and hour of a course (can't have any ; or : or ,)
    public void setCourse(String cname, String sec, int hour, String day) throws InvalidNameException {

        if (noPunctuation.noPunctuation(cname)
                || noPunctuation.noPunctuation(sec) || noPunctuation.noPunctuation(day)) {
            throw new InvalidNameException();
        }
        //Sets the course info
        this.courseName = cname;
        this.section = sec;
        this.hour = hour;
        this.day = day;

        System.out.println("Set course");
    }

    //MODIFIES: this
    //EFFECTS: adds an item to do under a certain course unless it is already added
    public void addItem(Item item) {
        if (!this.itemsToDo.contains(item)) {
            this.itemsToDo.add(item);
            item.setCourse(this);
        }
    }

    //Modifies: this
    //Effects: removes an item from the course
    public void removeItem(Item item) {
        int index = -1;
        for (int i = 0; i < itemsToDo.size(); i++) {
            Item currItem = itemsToDo.get(i);
            if (currItem.getDescription().equals(item.getDescription())
                    && currItem.getDueDate().equals(item.getDueDate())) {
                index = i;
            }
        }
        if (index != -1) {
            itemsToDo.remove(index);
            item.removeCourse(this);
        }
    }

    public ArrayList<Item> getItems() {
        return this.itemsToDo;
    }

    public String getDay() {
        return day;
    }


    //Effects: checks if an item is under this course
    public boolean containsItem(Item item) {
        for (Item addedItem: itemsToDo) {
            if (item.getDescription().equals(addedItem.getDescription())
                    && item.getDueDate().equals(addedItem.getDueDate())) {
                return true;
            }
        }
        return  false;
    }

    //Modifies: this
    //EFFECTS: tries to add an item to a course
    public void addUrgentItem(String name, String dueDate, Boolean completed) {
        try {
            addItem(new UrgentItem(name, dueDate, completed));
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
    }

    //Modifies: this
    //EFFECTS: tries to add an item to a course
    public void addRegularItem(String name, String dueDate, Boolean completed) {
        try {
            addItem(new RegularItem(name, dueDate, completed));
        } catch (InvalidNameException e) {
            System.out.println(e.presentException());
        }
    }



    //MODIFIES: this
    //EFFECTS: loads items into course.
    public void loadItems(List<String> itemsToDo) {
        for (String item : itemsToDo) {
            List<String> itemFields = splitter.splitBy(item, ":");
            if (itemFields.get(0).equals("urgent")) {
                addUrgentItem(itemFields.get(1), itemFields.get(2),
                        Boolean.parseBoolean(itemFields.get(3)));
            } else {
                addRegularItem(itemFields.get(1),
                        itemFields.get(2), Boolean.parseBoolean(itemFields.get(3)));
            }
        }
    }

    //EFFECTS: prints coursename section and hour
    public String toString() {
        return (this.courseName + " |" + this.section + " at " + Integer.toString(this.hour) + " on " + day);
    }

    //Modifies: text file
    //Effects: saves course information onto a text file
    @Override
    public String save(String fileName) {
        String itemsString = "";
        for (Item itemLeft: this.itemsToDo) {
            itemsString = itemsString + itemLeft.save() + ";";
        }
        return itemsString;
    }


    //Effects: determines if two courses are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return hour == course.hour
                && courseName.equals(course.courseName)
                && section.equals(course.section)
                && day.equals(course.day);
    }

    //effects: gets the hashCode
    @Override
    public int hashCode() {
        return Objects.hash(courseName, section, hour);
    }
}
