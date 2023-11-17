package App;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//WeatherService.java
@Service
public class WeatherService {

 @Autowired
 private LocationRepository locationRepository;

 @Autowired
 private WeatherDataRepository weatherDataRepository;

 @Value("${weatherstack.access_key}")
 private String weatherStackAccessKey;

 @Autowired
 private RestTemplate restTemplate;

 public class WeatherDataRetrievalException extends RuntimeException {
     public WeatherDataRetrievalException(String message) {
         super(message);
     }
 }

 public ResponseEntity<String> updateWeatherData(Location location) {
     String apiUrl = "http://api.weatherstack.com/current";

     UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
             .queryParam("access_key", weatherStackAccessKey)
             .queryParam("query", location.getLatitude() + "," + location.getLongitude());

     try {
         ResponseEntity<WeatherStackResponse> responseEntity = restTemplate.getForEntity(builder.toUriString(), WeatherStackResponse.class);

         if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
             WeatherStackResponse weatherStackResponse = responseEntity.getBody();

             if (weatherStackResponse.getCurrent() != null) {
                 WeatherData weatherData = new WeatherData();
                 weatherData.setLocation(location);
                 weatherData.setTemperature(weatherStackResponse.getCurrent().getTemperature());
                 weatherData.setRainfall(weatherStackResponse.getCurrent().getPrecip());
                 weatherDataRepository.save(weatherData);
                 return ResponseEntity.ok("Weather data updated successfully.");
             } else {
                 locationRepository.delete(location);
                 throw new WeatherDataRetrievalException("Error: WeatherStack API response does not contain valid weather data. Location entry deleted.");
             }
         } else {
             locationRepository.delete(location);
             throw new WeatherDataRetrievalException("Location does not have a weather station.");
         }
     } catch (HttpClientErrorException e) {
         locationRepository.delete(location);
         throw new WeatherDataRetrievalException("Error: WeatherStack API client error. Location entry deleted.");
         
     } catch (HttpServerErrorException e) {
         locationRepository.delete(location);
         throw new WeatherDataRetrievalException("Error: WeatherStack API server error. Location entry deleted.");
         
     } catch (RestClientException e) {
         locationRepository.delete(location);
         throw new WeatherDataRetrievalException("Error: WeatherStack API request failed. Location entry deleted.");
     }
 }

 public void refreshAllWeatherData() {
     List<Location> locations = locationRepository.findAll();

     for (Location location : locations) {
         try {
             weatherDataRepository.deleteByLocation(location);
             updateWeatherData(location);
         } catch (Exception e) {
         }
     }
 }
}
