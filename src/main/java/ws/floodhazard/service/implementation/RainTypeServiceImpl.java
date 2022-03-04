package ws.floodhazard.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import ws.floodhazard.integration.entity.Location;
import ws.floodhazard.integration.entity.RainType;
import ws.floodhazard.integration.repository.RainTypeRepository;
import ws.floodhazard.service.RainTypeService;
import ws.floodhazard.util.Constants;

import java.util.*;

import static ws.floodhazard.util.Constants.VIOLENT_RAIN;

@Slf4j
@Service
public class RainTypeServiceImpl extends AbstractService<RainType> implements RainTypeService {


    @Autowired
    private RainTypeRepository repository;

    @Transactional
    @Override
    public String addRainType(RainType item) {
        RainType existingItem = repository.findOneByRainLevel(item.getRainLevel());
        if(Objects.nonNull(existingItem)) {
            existingItem.setMaximumPrecipitationRate(item.getMaximumPrecipitationRate());
            existingItem.setMinimumPrecipitationRate(item.getMinimumPrecipitationRate());
            existingItem.setRainLevel(item.getRainLevel());
            existingItem.setRainTypeDetail(item.getRainTypeDetail());
            return response(repository.save(existingItem));
        }
        return response(repository.save(item));
    }

    @Override
    public List<RainType> getAllRainType() {
        return (List<RainType>) repository.findAll();
    }

    @Override
    public Optional<String> getRainType(double intensity) {

        if(intensity > 50)
            return Optional.of(VIOLENT_RAIN);

        RainType rainType = repository.getRate(intensity);
        log.debug("RAIN TYPE {}", rainType);
        if(!ObjectUtils.isEmpty(rainType))
            return Optional.of(rainType.getRainLevel());

        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteRainType(String id) {
        log.debug("DELETE RAIN TYPE WITH ID {}", id);
        Optional<RainType> existingItem = repository.findById(Long.valueOf(id));
        if(existingItem.isPresent())
            repository.deleteById(Long.valueOf(id));
    }

    @Transactional
    @Override
    public String initializeRainType() {
        log.info("Start Initialize Rain Types");
        RainType lightRain = RainType.builder().
                minimumPrecipitationRate(0).
                maximumPrecipitationRate(2.5).
                rainLevel("Light Rain").
                rainTypeDetail("When the precipitation rate is < 2.5 mm (0.098 in) per hour").
                createDate(new Date()).
                build();
        RainType moderateRain = RainType.builder().
                minimumPrecipitationRate(2.5).
                maximumPrecipitationRate(7.6).
                rainLevel("Moderate Rain").
                rainTypeDetail("When the precipitation rate is between 2.5 mm (0.098 in) - 7.6 mm (0.30 in) or 10 mm (0.39 in) per hour").
                createDate(new Date()).
                build();
        RainType heavyRain = RainType.builder().
                minimumPrecipitationRate(7.6).
                maximumPrecipitationRate(50).
                rainLevel("Heavy Rain").
                rainTypeDetail("when the precipitation rate is > 7.6 mm (0.30 in) per hour,[105] or between 10 mm (0.39 in) and 50 mm (2.0 in) per hour").
                createDate(new Date()).
                build();
        RainType violentRain = RainType.builder().
                minimumPrecipitationRate(50).
                maximumPrecipitationRate(900).
                rainLevel("Violent Rain").
                rainTypeDetail("When the precipitation rate is > 50 mm (2.0 in) per hour").
                createDate(new Date()).
                build();
        List<RainType> rainTypes = Arrays.asList(lightRain,moderateRain,heavyRain,violentRain);
        repository.saveAll(rainTypes);
        log.info("Done initializing Rain Types");
       return Constants.ADD_SUCCESS;
    }
}
