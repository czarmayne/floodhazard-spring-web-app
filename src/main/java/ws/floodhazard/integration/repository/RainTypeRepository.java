package ws.floodhazard.integration.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ws.floodhazard.integration.entity.RainType;

@Repository
public interface RainTypeRepository extends CrudRepository<RainType, Long> {

    RainType findOneByRainLevel(String rainType);

    @Query("select rt from RainType rt where rt.minimumPrecipitationRate <= :rate and rt.maximumPrecipitationRate > :rate ORDER BY rt.id ASC")
    RainType getRate(@Param("rate") double rate);

}
