package courses.item;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import courses.Splitter;
import exceptions.InvalidNameException;

import java.time.LocalDate;
///I found the local date code from https://www.javatpoint.com/java-get-current-date

public class UrgentItem extends Item {
    private Splitter splitter = new Splitter();

    public UrgentItem(String description, String dueDate, boolean completed) throws InvalidNameException {
        super(description, dueDate, completed);
        this.type = "urgent";
    }

    @Override
    public String getType() {
        return type;
    }

    //Effects: returns characteristics of item and if its due soon
    @Override
    public String toString() {
        if (dueSoon()) {
            return "(" + completed + ")" + super.toString() + " *DUE SOON*";
        } else {
            return "(" + completed + ")" + super.toString();
        }

    }

    //Effects:determines if the urgent item is due in the next 10 days
    private boolean dueSoon() {
        ArrayList<String> dueDateArray = splitter.splitBy(this.dueDate, "/");

        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        date2 = date2.withMonth(Integer.parseInt(dueDateArray.get(1)));
        date2 = date2.withDayOfMonth(Integer.parseInt(dueDateArray.get(0)));

        int dueDateDay = date2.getDayOfYear();
        int currentDay = date1.getDayOfYear();

        if ((dueDateDay - currentDay) <= 10 && (dueDateDay - currentDay) >= 0) {
            return true;
        }

        return false;
    }

}
