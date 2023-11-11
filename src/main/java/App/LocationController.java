package App;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//LocationController.java
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
 public ResponseEntity<Location> addLocation(@RequestBody Location location) {
     Location savedLocation = locationRepository.save(location);
     weatherService.updateWeatherData(savedLocation);
     return ResponseEntity.ok(savedLocation);
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
     Optional<Location> locationOptional = locationRepository.findById(id);

     if (locationOptional.isPresent()) {
         Location location = locationOptional.get();
         
         // Delete corresponding entries in WeatherData table
         List<WeatherData> weatherDataList = weatherDataRepository.findByLocation(location);
         weatherDataRepository.deleteAll(weatherDataList);

         // Delete the location entry
         locationRepository.deleteById(id);

         return ResponseEntity.noContent().build();
     } else {
         return ResponseEntity.notFound().build();
     }
 }
}
