package App;

import org.springframework.data.jpa.repository.JpaRepository;

//LocationRepository.java
public interface LocationRepository extends JpaRepository<Location, Long> {
}
