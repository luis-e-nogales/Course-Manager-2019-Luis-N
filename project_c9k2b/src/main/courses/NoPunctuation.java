package courses;

public class NoPunctuation {

    //Effects: determines if a string contains the three types of punctuations
    public Boolean noPunctuation(String s) {
        return (s.contains(";") || s.contains(":") || s.contains(","));
    }
}
