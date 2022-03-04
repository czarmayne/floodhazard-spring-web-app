package ws.floodhazard.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.floodhazard.integration.entity.SensitivityCondition;
import ws.floodhazard.integration.repository.SensitivityConditionRepository;
import ws.floodhazard.service.PrecipitationConditionService;

import java.util.*;

@Slf4j
@Service
public class PrecipitationConditionServiceImpl extends AbstractService<SensitivityCondition> implements PrecipitationConditionService {

    @Autowired
    private SensitivityConditionRepository repository;

    @Transactional
    @Override
    public String addPrecipitationCondition(SensitivityCondition item) {
        SensitivityCondition existingItem = repository.findOneBySensitivityLevel(item.getSensitivityLevel());
        if(Objects.nonNull(existingItem)) {
            existingItem.setSensitivityDetail(item.getSensitivityDetail());
            existingItem.setSensitivityLevel(item.getSensitivityLevel());
            return response(repository.save(existingItem));
        }
        return response(repository.save(item));
    }

    @Override
    public List<SensitivityCondition> getAllPrecipitationCondition() {
        return (List<SensitivityCondition>) repository.findAll();
    }

    @Transactional
    @Override
    public void deleteSensitivityCondition(String id) {
        log.debug("DELETE RAIN TYPE WITH ID {}", id);
        Optional<SensitivityCondition> existingItem = repository.findById(Long.valueOf(id));
        if(existingItem.isPresent())
            repository.deleteById(Long.valueOf(id));
    }

    @Override
    public void initializeSensitivityConditions() {
        log.info("Start initializting sensitivity conditions");
        SensitivityCondition red = SensitivityCondition.builder()
                .sensitivityDetail("RED")
                .sensitivityLevel("Not passable by small vehicles and not passable by children and Adult and Violent flood")
                .createDate(new Date())
                .build();
        SensitivityCondition orange = SensitivityCondition.builder()
                .sensitivityDetail("ORANGE")
                .sensitivityLevel("Below knee flood in 2 cosecutive hours and 4 cosecutive hours is above knee flood - Not passable by small vehicles and not passable by Children and Adult")
                .createDate(new Date())
                .build();
        SensitivityCondition yellow = SensitivityCondition.builder()
                .sensitivityDetail("YELLOW")
                .sensitivityLevel("Below knee flood in 4 hours")
                .createDate(new Date())
                .build();
        SensitivityCondition green = SensitivityCondition.builder()
                .sensitivityDetail("GREEN")
                .sensitivityLevel("Moderate Rainfall")
                .createDate(new Date())
                .build();
        repository.saveAll(Arrays.asList(red, orange, yellow, green));
        log.info("Done initializting sensitivity conditions");
    }
}
