package weather;

public class WeatherPrinter implements Observer {

    //Effects:Prints current weather status as observed from linked weather
    @Override
    public String update(Weather weather) {
        Double temp = weather.getTemp();
        Double maxTemp = weather.getMaxTemp();
        Double minTemp = weather.getMinTemp();
        Double windSpeed = weather.getWindSpeed();
        String description = weather.getDescription();

        String firstLine = "Current Temperature is: " + temp + " deg C" + ", min: " + minTemp + ", max: " + maxTemp;
        String secondLine = "wind speed is " + windSpeed + " km";
        String thirdLine = "weather is described as: " + description;


        System.out.println(firstLine);
        System.out.println(secondLine);
        System.out.println(thirdLine);

        return firstLine + secondLine + thirdLine;
    }

}
