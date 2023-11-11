package App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//WeatherService.java
@Service
public class WeatherService {

 @Autowired
 private WeatherDataRepository weatherDataRepository;

 @Value("${weatherstack.access_key}") // Add this if not already in your configuration
 private String weatherStackAccessKey;

 @Autowired
 private RestTemplate restTemplate; // You need to configure RestTemplate in your application

 public void updateWeatherData(Location location) {
     String apiUrl = "http://api.weatherstack.com/current";
     
     UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
             .queryParam("access_key", weatherStackAccessKey)
             .queryParam("query", location.getLatitude() + "," + location.getLongitude());

     try {
         ResponseEntity<WeatherStackResponse> responseEntity = restTemplate.getForEntity(builder.toUriString(), WeatherStackResponse.class);

         if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
             WeatherStackResponse weatherStackResponse = responseEntity.getBody();
             
             // Create WeatherData entity and save it
             WeatherData weatherData = new WeatherData();
             weatherData.setLocation(location);
             weatherData.setTemperature(weatherStackResponse.getCurrent().getTemperature());
             weatherData.setRainfall(weatherStackResponse.getCurrent().getPrecip());
             weatherDataRepository.save(weatherData);
         }
     } catch (RestClientException e) {
         // Handle exception, log error, etc.
     }
 }
}
