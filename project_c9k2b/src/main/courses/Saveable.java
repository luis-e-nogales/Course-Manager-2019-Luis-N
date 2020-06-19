package courses;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface Saveable {

    //Modifies: text file
    //Effects: saves information onto a text file
    public String save(String fileName) throws FileNotFoundException, UnsupportedEncodingException;
}
