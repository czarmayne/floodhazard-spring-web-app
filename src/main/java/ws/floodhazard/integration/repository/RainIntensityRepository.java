package ws.floodhazard.integration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ws.floodhazard.integration.entity.RainIntensity;

import java.util.List;

@Repository
public interface RainIntensityRepository extends CrudRepository<RainIntensity, Long> {

    RainIntensity findOneByIntensityAndHourAndDate(double rainIntensity, int hour, String date);
    RainIntensity findOneByHourAndDate(int hour, String date);
    List<RainIntensity> findByDate(String date);
    Page<RainIntensity> findByDate(String date, Pageable pageable);
    Page<RainIntensity> findAll(Pageable pageable);

    @Query(value = "SELECT SUM(INTENSITY) AS INTENSITY FROM RAIN_INTENSITY WHERE HOUR <= :hour AND DATUM = :datum", nativeQuery = true)
//    @Query(value = "SELECT SUM(INTENSITY) AS INTENSITY FROM tb.RAIN_INTENSITY WHERE HOUR <= :hour AND DATUM = :datum", nativeQuery = true)
    Double getForecast(@Param("hour") int hour, @Param("datum") String date);
}
