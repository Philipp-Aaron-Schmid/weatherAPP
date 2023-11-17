package App;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
	List<WeatherData> findByLocation(Location location);

	void deleteByLocation(Location location);

	List<WeatherData> findByLocationId(Long locationId);
}
