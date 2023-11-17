package App;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WeatherData {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne
@JoinColumn(name = "location_id")
private Location location;

private double temperature;
private double rainfall;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Location getLocation() {
    return location;
}

public void setLocation(Location location) {
    this.location = location;
}

public double getTemperature() {
    return temperature;
}

public void setTemperature(double temperature) {
    this.temperature = temperature;
}

public double getRainfall() {
    return rainfall;
}

public void setRainfall(double rainfall) {
    this.rainfall = rainfall;
}
}