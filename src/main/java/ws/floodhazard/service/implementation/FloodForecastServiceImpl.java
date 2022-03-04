package ws.floodhazard.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import ws.floodhazard.integration.entity.Sensitivity;
import ws.floodhazard.integration.entity.SensitivityCondition;
import ws.floodhazard.integration.entity.SensitivityDTO;
import ws.floodhazard.integration.repository.SensitivityConditionRepository;
import ws.floodhazard.integration.repository.SensitivityRepository;
import ws.floodhazard.service.FloodForecastService;
import ws.floodhazard.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FloodForecastServiceImpl extends AbstractService<Sensitivity> implements FloodForecastService {

    @Autowired
    private SensitivityRepository repository;
    @Autowired
    private SensitivityConditionRepository sensitivityConditionRepository;

    @Transactional
    @Override
    public String addRainForecast(Sensitivity item) {
        log.debug("addRainForecast >>>> \n {}", item.toString());
        Sensitivity existingItem = repository.findOneByLocationAndHourAndDate(item.getLocation(), item.getHour(), item.getDate());
        if(Objects.nonNull(existingItem)) {
            existingItem.setIntensity(item.getIntensity());
            existingItem.setSensitivityDetail(item.getSensitivityDetail());
            existingItem.setSensitivityLevel(item.getSensitivityLevel());
            return response(repository.save(existingItem));
        }

        Sensitivity newItem = Sensitivity.builder()
                .location(item.getLocation())
                .date(item.getDate())
                .hour(item.getHour())
                .intensity(item.getIntensity())
                .sensitivityDetail(item.getSensitivityDetail())
                .sensitivityLevel(item.getSensitivityLevel())
                .build();
        return response(repository.save(newItem));
    }

    @Override
    public List<Sensitivity> getFloodForecast() {
        log.debug("getFloodForecast >>>> \n");
        return (List<Sensitivity>) repository.findAll();
    }

    @Override
    public List<SensitivityDTO> getFloodForecast(Date date) {
        log.debug("getFloodForecast >>>> \n Date {}", date);

        List<SensitivityDTO> response = new ArrayList<>();

        repository.findAllByDateOrderByHourAsc(DateUtils.getDate(date)).stream().forEach(i -> {
            String sensitivityDetail = org.apache.commons.lang3.StringUtils.isBlank(i.getSensitivityDetail()) ? org.apache.commons.lang3.StringUtils.EMPTY : i.getSensitivityDetail();
            String sensitivityLevel = org.apache.commons.lang3.StringUtils.isBlank(i.getSensitivityLevel()) ? org.apache.commons.lang3.StringUtils.EMPTY : i.getSensitivityLevel();
            SensitivityDTO item = SensitivityDTO.builder()
                    .location(i.getLocation())
                    .intensity(String.valueOf(i.getIntensity()))
                    .date(i.getDate())
                    .hour(i.getHour())
                    .sensitivityLevel(sensitivityLevel)
                    .sensitivityDetail(sensitivityDetail)
                    .build();
            response.add(item);
        });

        return response;
    }

    @Override
    public Page<Sensitivity> getFloodForecastByDate(Date date, Pageable pageable) {
        log.debug("getFloodForecastByDate >>>> \n Date {} , Pageable {}", date, pageable);
        return repository.findAllByDate(DateUtils.getDate(date), pageable);
    }

    @Override
    public Page<Sensitivity> getFloodForecast(Pageable pageable) {
        log.debug("getFloodForecast >>>> \n {}", pageable);
        return repository.findAll(pageable);
    }

    @Override
    public void deleteForecast(String date, String hour, String location) {
        log.debug("deleteForecast >>>> \n Date {}, Hour {}", date, hour);
        Sensitivity existingItem = repository.findOneByLocationAndHourAndDate(location, Integer.parseInt(hour), date);
        if(!ObjectUtils.isEmpty(existingItem))
            repository.delete(existingItem);
    }
}
