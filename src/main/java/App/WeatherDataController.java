package App;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//WeatherDataController.java
@RestController
@RequestMapping("/weatherData")
public class WeatherDataController {

 @Autowired
 private WeatherDataRepository weatherDataRepository;

 @GetMapping("/{id}")
 public ResponseEntity<?> getWeatherDataById(@PathVariable Long id) {
     try {
         Optional<List<WeatherData>> weatherDataOptional = Optional.of(weatherDataRepository.findByLocationId(id));
         if (weatherDataOptional.isPresent()) {
             List<WeatherData> weatherData = weatherDataOptional.get();
             return ResponseEntity.ok(weatherData);
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Weather data with ID " + id + " not found.");
         }
     } catch (Exception e) {
         // Log the exception or handle it accordingly
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Failed to retrieve weather data. " + e.getMessage());
     }
 }
}

