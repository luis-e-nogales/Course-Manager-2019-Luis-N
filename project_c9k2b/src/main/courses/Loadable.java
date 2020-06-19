package courses;

import java.io.IOException;

public interface Loadable {

    //Modifies: this
    //Effects: loads information from a text file onto the program.
    public void load(String fileName) throws IOException;
}
