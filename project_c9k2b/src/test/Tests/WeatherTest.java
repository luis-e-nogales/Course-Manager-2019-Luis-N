package Tests;

import weather.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherTest {

    private WeatherHandler weatherHandler;
    private Weather weather;
    private Weather weather1;
    private WeatherPrinter weatherPrinter;

    @BeforeEach
    public void setUp() {
        weatherHandler = new WeatherHandler();
        weather1 = new Weather(12.0,15.0,10.0,2.0,
                "cloud",null);
        weather = new Weather(32.0,50.0,15.0,0.0,
                "SUNNY", null);
        weatherPrinter = new WeatherPrinter();
    }

    @Test
    public void updateTest() {
        assertEquals(weatherPrinter.update(weather1), "Current Temperature is: 12.0 deg C, min: 10.0, max: 15.0"
                + "wind speed is 2.0 km" + "weather is described as: cloud");
    }

    @Test
    public void addObserverTest() {
        assertEquals(weather.getObservers().size(), 0);
        weather.addObserver(weatherPrinter);
        assertEquals(weather.getObservers().size(), 1);
    }

    @Test
    public void setWeatherTest() {
        assertEquals(weather.getTemp(), 32);
        weather.addObserver(weatherPrinter);
        weather.setWeather(new Weather(20.0,40.0,15.7,0.2,
                "SUNISH", null));
        assertEquals(weather.getDescription(), "SUNISH");
        assertEquals(weather.getTemp(), 20.0);
        assertEquals(weather.getMaxTemp(), 40.0);
        assertEquals(weather.getMinTemp(), 15.7);
        assertEquals(weather.getWindSpeed(), 0.2);

    }

    @Test
    public void whGettersTest() {
        assertEquals(weatherHandler.getWeather().getTemp(), 0.0);
        assertNotNull(weatherHandler.getWeatherPrinter());

        weatherHandler.checkWeather(weather);
        assertEquals(weatherHandler.getWeather().getTemp(), 32.0);
    }

}
