package App;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    // AddLocation Test
    @Test
    void addLocation_shouldReturnOk() throws Exception {
        String locationJson = "{ \"location\": \"TestLocation\", \"label\": \"TestLabel\", \"latitude\": 10.0, \"longitude\": 20.0 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/locations")
                .content(locationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // DeleteLocation Test
    @Test
    void deleteLocation_shouldReturnOk() throws Exception {
        // Assuming there's a location with ID 1
        mockMvc.perform(MockMvcRequestBuilders.delete("/locations/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteLocation_shouldReturnNotFound() throws Exception {
        // Assuming there's no location with ID 100
        mockMvc.perform(MockMvcRequestBuilders.delete("/locations/100"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // RefreshWeatherData Test
    @Test
    void refreshWeatherData_shouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/locations/refresh"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // GetAllLocations Test
    @Test
    void getAllLocations_shouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/locations"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // GetWeatherDataById Test
    @Test
    void getWeatherDataById_shouldReturnOk() throws Exception {
        // Assuming there's a WeatherData with ID 1
        mockMvc.perform(MockMvcRequestBuilders.get("/weatherData/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getWeatherDataById_shouldReturnNotFound() throws Exception {
        // Assuming there's no WeatherData with ID 100
        mockMvc.perform(MockMvcRequestBuilders.get("/weatherData/100"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
