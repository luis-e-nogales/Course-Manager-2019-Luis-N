package weather;

public class WeatherHandler {
    private Weather weather;
    private WeatherPrinter weatherPrinter;

    //Effects: sets up weather handler
    public WeatherHandler() {
        weather = new Weather(0.0, 0.0, 0.0, 0.0, "", null);
        weatherPrinter = new WeatherPrinter();
        weather.addObserver(weatherPrinter);
    }

    //EFFECTS: sets the weather in the weather handler to the new updated weather
    public void checkWeather(Weather updatedWeather) {
        weather.setWeather(updatedWeather);
    }

    public Weather getWeather() {
        return weather;
    }

    public WeatherPrinter getWeatherPrinter() {
        return weatherPrinter;
    }
}
