package App;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherStackResponse {

    @JsonProperty("current")
    private CurrentWeather current;


    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }
}

class CurrentWeather {

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("precip")
    private double precip;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }
}
