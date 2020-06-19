package weather;

public interface Observer {

    //Effects: updates the weather
    public String update(Weather weather);
}
