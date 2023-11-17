package App;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationController {

 @Autowired
 private LocationRepository locationRepository;
 
 @Autowired
 private WeatherDataRepository weatherDataRepository;

 @Autowired
 private WeatherService weatherService;

 @PostMapping
 public ResponseEntity<?> addLocation(@RequestBody Location location) {
     try {
         Location savedLocation = locationRepository.save(location);
         weatherService.updateWeatherData(savedLocation);
         return ResponseEntity.ok(savedLocation);
     } catch (WeatherService.WeatherDataRetrievalException e) {
         // Handle the custom exception (WeatherDataRetrievalException)
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
     } catch (Exception e) {
         // Handle other exceptions
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Failed to add location. " + e.getMessage());
     }
 }

 @Transactional
 @DeleteMapping("/{id}")
 public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
     try {
         Optional<Location> locationOptional = locationRepository.findById(id);
         if (locationOptional.isPresent()) {
             Location location = locationOptional.get();

             // Delete corresponding weather data
             weatherDataRepository.deleteByLocation(location);

             // Delete the location
             locationRepository.deleteById(id);

             return ResponseEntity.ok("Deletion successful for location with ID " + id);
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Location with ID " + id + " not found.");
         }
     } catch (Exception e) {
         // Log the exception or handle it accordingly
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Failed to delete location. " + e.getMessage());
     }
 }

 @GetMapping("/refresh")
 public ResponseEntity<String> refreshWeatherData() {
     try {
         // Call a method in WeatherService to refresh weather data for all locations
         // You might need to modify the WeatherService to handle this specific case
         weatherService.refreshAllWeatherData();
         return ResponseEntity.ok("Refresh Successful");
     } catch (Exception e) {
         // Log the exception or handle it accordingly
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Refresh failed: " + e.getMessage());
     }
 }

 @GetMapping
 public ResponseEntity<List<Location>> getAllLocations() {
     List<Location> locations = locationRepository.findAll();
     return ResponseEntity.ok(locations);
 }
}
