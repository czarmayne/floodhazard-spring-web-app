package ws.floodhazard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import ws.floodhazard.service.LocationService;
import ws.floodhazard.service.PrecipitationConditionService;
import ws.floodhazard.service.RainIntensityService;
import ws.floodhazard.service.RainTypeService;
import ws.floodhazard.util.Constants;
import ws.floodhazard.util.DateUtils;

@Slf4j
@SpringBootApplication
public class Application {
//        implements CommandLineRunner {

    @Autowired
    RainTypeService rainTypeService;
    @Autowired
    LocationService locationService;
    @Autowired
    PrecipitationConditionService precipitationConditionService;
    @Autowired
    RainIntensityService rainIntensityService;

    public void initialize() {
        log.info("Start to initialize static data");
        locationService.initializeLocations();
        precipitationConditionService.initializeSensitivityConditions();
        rainTypeService.initializeRainType();
        rainIntensityService.createRainIntensityInitData(null);
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        initialize();
//    }
}


