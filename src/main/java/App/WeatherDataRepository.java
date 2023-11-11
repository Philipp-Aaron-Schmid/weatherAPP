package App;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//WeatherDataRepository.java
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
	List<WeatherData> findByLocation(Location location);
}
