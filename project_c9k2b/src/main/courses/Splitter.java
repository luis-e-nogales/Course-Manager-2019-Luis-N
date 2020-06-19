package courses;

import java.util.ArrayList;
import java.util.Arrays;

public class Splitter {

    //Effects: splits a line according to a certain character
    public ArrayList<String> splitBy(String currentLine, String character) {
        String[] splits = currentLine.split(character);
        return new ArrayList<>(Arrays.asList(splits));
    }
    //ArrayList<String> splitBy(String currentLine, String character);
}
