package ws.floodhazard.integration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ws.floodhazard.integration.entity.Sensitivity;

import java.util.Date;
import java.util.List;

@Repository
public interface SensitivityRepository extends CrudRepository<Sensitivity, Long> {

    Sensitivity findOneByLocationAndHourAndDate(String location, int hour, String date);

//    @Query(value = "SELECT " +
//            "'1' AS SENSITIVITY_ID, " +
//            " SUM(RAIN_INTENSITY) AS SE, " +
//            " LOCATION, " +
//            " '' AS SENSITIVITY_LEVEL, " +
//            " '' AS SENSITIVITY_DETAIL, " +
//            " '' AS HOUR, " +
//            " ':date' AS DATE, " +
//            " '' AS CREATE_DATE " +
//            "FROM TB.SENSITIVITY " +
//            "WHERE " +
//            "LOCATION = :location " +
//            "AND  " +
//            "HOUR < :hour " +
//            "AND DATE = :date " +
//            "GROUP BY LOCATION", nativeQuery = true)
    @Query(value = "SELECT " +
            "'1' AS SENSITIVITY_ID, " +
            " SUM(RAIN_INTENSITY) AS SE, " +
            " LOCATION, " +
            " '' AS SENSITIVITY_LEVEL, " +
            " '' AS SENSITIVITY_DETAIL, " +
            " '' AS HOUR, " +
            " ':date' AS DATE, " +
            " '' AS CREATE_DATE " +
            "FROM SENSITIVITY " +
            "WHERE " +
            "LOCATION = :location " +
            "AND  " +
            "HOUR < :hour " +
            "AND DATE = :date " +
            "GROUP BY LOCATION", nativeQuery = true)
    Sensitivity getNextForecast(@Param("location") String location,@Param("hour") int hour,@Param("date") String date);

    List<Sensitivity> findAllByDateOrderByHourAsc(String date);

    Page<Sensitivity> findAll(Pageable pageable);

    Page<Sensitivity> findAllByDate(String date, Pageable pageable);
}
