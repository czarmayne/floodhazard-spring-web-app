package ws.floodhazard.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import ws.floodhazard.integration.entity.*;
import ws.floodhazard.integration.repository.RainIntensityRepository;
import ws.floodhazard.integration.repository.SensitivityRepository;
import ws.floodhazard.service.*;
import ws.floodhazard.util.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RainIntensityServiceImpl extends AbstractService<RainIntensity> implements RainIntensityService {

    @Autowired
    private RainIntensityRepository repository;
    @Autowired
    private RainTypeService rainTypeService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private PrecipitationConditionService precipitationConditionService;
    @Autowired
    private SensitivityRepository sensitivityRepository;
    @Autowired
    private FloodForecastService floodForecastService;

    @Override
    public Page<RainIntensity> getRainIntensityByDate(Date date, Pageable pageable) {
        log.debug("getRainIntensityByDate >> {}", date);
        return repository.findByDate(DateUtils.getDate(date), pageable);
    }

    @Override
    public Page<RainIntensity> getRainIntensity(Pageable pageable) {
        log.debug("getRainIntensity >>");
        return repository.findAll(pageable);
    }

    @Transactional
    @Override
    public String addRainIntensity(RainIntensity item) {
        log.debug("ADD RAIN INTENSITY ================================ \n {}", item.toString());
        RainIntensity existingItem = repository.findOneByHourAndDate(item.getHour(), item.getDate());
        Optional<String> rainType = rainTypeService.getRainType(item.getIntensity());
        String response = StringUtils.EMPTY;
        if(Objects.nonNull(existingItem)) {
            log.debug("UPDATE RAIN INTENSITY \n date: {}, hour: {}, intensity {}", item.getIntensity(), item.getHour() , item.getDate());
            existingItem.setIntensity(item.getIntensity());
            existingItem.setType(rainType.get());
            response = response(repository.save(existingItem));
            this.forecastRain(existingItem.getDate());
            return response;
        }

        RainIntensity newIntensity = RainIntensity.builder()
                .intensity(item.getIntensity())
                .type(rainType.isPresent() ? rainType.get() : "DEFAULT")
                .date(item.getDate())
                .hour(item.getHour())
                .createDate(new Date())
                .build();
        response = response(repository.save(newIntensity));
        this.forecastRain(item.getDate());
        return response;
    }

    @Override
    public List<RainIntensity> createRainIntensityInitData(Date date) {
        String dateStr = DateUtils.getDate(date);
        log.debug("createRainIntensityInitData >>>> \n {}", dateStr);
        List<RainIntensity> existingItems = repository.findByDate(dateStr);
        log.info("INTENSITY SIZE {}", existingItems.size());
        if(ObjectUtils.isEmpty(existingItems) || existingItems.size() < 23) {
            log.debug("NOT EXISTING LISTING FOR THE DATE {}", dateStr);
            List<RainIntensity> rainIntensityList = new ArrayList<>();
            for (Long i = 0L; i < 24L; i++) {
                RainIntensity item = RainIntensity.builder()
                        .id(i)
                        .intensity(0)
                        .hour(i.intValue())
                        .date(dateStr)
                        .createDate(new Date())
                        .build();
                log.debug("ADD INTENSITY \n {}", item.toString());
                this.addRainIntensity(item);
                rainIntensityList.add(item);
            }
            return rainIntensityList;
        }
        return existingItems;
    }

    @Override
    public List<RainIntensity> getAllRainIntensity() {
        log.debug("getAllRainIntensity >>>> \n");
        return (List<RainIntensity>) repository.findAll();
    }

    @Override
    public void deleteIntensity(String date, String hour) {
        log.debug("deleteIntensity >>>> \n {}, {}", date, hour);
        RainIntensity item = repository.findOneByHourAndDate(Integer.parseInt(hour), date);
        if(!ObjectUtils.isEmpty(item))
            repository.delete(item);
    }

    public void forecastRain(String date) {
        log.debug("forecastRain >>>> \n {}, {}, {}", date);
        List<Location> locations = locationService.getAllLocation();
        List<SensitivityCondition> sensitivityConditions = precipitationConditionService.getAllPrecipitationCondition();
        Map<String, SensitivityCondition> conditions = new HashMap<>();
        for(SensitivityCondition sensitivityCondition : sensitivityConditions) {
            conditions.put(sensitivityCondition.getSensitivityDetail(), sensitivityCondition);
        }

        locations.stream().forEach(
                location -> {
                    List<Integer> hourList = Arrays.asList(2,4,6,8,10,12,14,16,18,20,22);
                    hourList.stream().forEach(hour -> {
                        Double forecastIntensity = repository.getForecast(hour, date);
                        log.debug("LOCATION : {} HOUR {} , DATE {}, INTENSITY {}", location.getName(),hour, date, forecastIntensity);
                        double intensityResult = Objects.nonNull(forecastIntensity)? forecastIntensity : 0.0;
                        Sensitivity sensitivity = Sensitivity.builder()
                                .intensity(intensityResult)
                                .date(date)
                                .hour(hour)
                                .location(location.getName())
                                .build();
                        if(intensityResult <= location.getLevelOne()) {
                            log.debug("LEVEL ONE");
                            sensitivity.setSensitivityLevel(conditions.get("GREEN").getSensitivityLevel());
                            sensitivity.setSensitivityDetail(conditions.get("GREEN").getSensitivityDetail());
                        } else if(intensityResult > location.getLevelOne() && intensityResult <= location.getLevelTwo()) {
                            log.debug("LEVEL TWO");
                            sensitivity.setSensitivityLevel(conditions.get("YELLOW").getSensitivityLevel());
                            sensitivity.setSensitivityDetail(conditions.get("YELLOW").getSensitivityDetail());
                        } else if(intensityResult > location.getLevelTwo() && intensityResult <= location.getLevelThree()) {
                            log.debug("LEVEL THREE");
                            sensitivity.setSensitivityLevel(conditions.get("ORANGE").getSensitivityLevel());
                            sensitivity.setSensitivityDetail(conditions.get("ORANGE").getSensitivityDetail());
                        } else if((intensityResult > location.getLevelThree() && intensityResult <= location.getLevelFour()) || location.getLevelFour() <= intensityResult ) {
                            log.debug("LEVEL FOUR");
                            sensitivity.setSensitivityLevel(conditions.get("RED").getSensitivityLevel());
                            sensitivity.setSensitivityDetail(conditions.get("RED").getSensitivityDetail());
                        }

                        if(isThereASensitivityForNotification(sensitivity))
                            floodForecastService.addRainForecast(sensitivity);
                    });
                }
        );

    }

    @Override
    public List<String> getListDates() {
        log.info("Get list of dates");
        List<RainIntensity> rainIntensityList = (List)repository.findAll();
        return rainIntensityList.stream().filter(i->Objects.nonNull(i.getDate())).map(i->i.getDate()).distinct().collect(Collectors.toList());
    }

    private boolean isThereASensitivityForNotification(Sensitivity sensitivity) {
        return StringUtils.isNotBlank(sensitivity.getSensitivityDetail()) && StringUtils.isNotBlank(sensitivity.getSensitivityLevel());
    }

}
