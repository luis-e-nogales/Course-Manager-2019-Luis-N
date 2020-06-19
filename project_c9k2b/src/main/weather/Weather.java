package weather;

import java.awt.*;

public class Weather extends Subject {
    private Double temp;
    private Double maxTemp;
    private Double minTemp;
    private Double windSpeed;
    private String description;
    private Image image;


    public Weather(Double temp, Double maxTemp, Double minTemp, Double windSpeed, String description, Image image) {
        this.temp = temp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.windSpeed = windSpeed;
        this.description = description;
        this.image = image;
    }

    //MODIFIES: this
    //EFFECTS: sets weather and notifies all observers
    public void setWeather(Weather newWeather) {
        this.temp = newWeather.getTemp();
        this.maxTemp = newWeather.getMaxTemp();
        this.minTemp = newWeather.getMinTemp();
        this.windSpeed = newWeather.getWindSpeed();
        this.description = newWeather.getDescription();

        notifyObservers(newWeather);
    }

    public Double getTemp() {
        return temp;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }


}
