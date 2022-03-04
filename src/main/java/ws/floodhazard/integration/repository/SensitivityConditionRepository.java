package ws.floodhazard.integration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ws.floodhazard.integration.entity.SensitivityCondition;

@Repository
public interface SensitivityConditionRepository extends CrudRepository<SensitivityCondition, Long> {

    SensitivityCondition findOneBySensitivityLevel(String sensitivityLevel);
    SensitivityCondition findOneBySensitivityDetail(String sensitivityDetails);
}
