package courses.item;

import exceptions.InvalidNameException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class RegularItem extends Item {
    public RegularItem(String description, String dueDate, boolean completed) throws InvalidNameException {
        super(description, dueDate, completed);
        type = "regular";
    }

    @Override
    public String getType() {
        return type;
    }

    //Effects: returns the characteristics of the item
    @Override
    public String toString() {
        return "(" + completed + ")" + super.toString();
    }
    
}
