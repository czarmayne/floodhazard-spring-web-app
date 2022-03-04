package ws.floodhazard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ws.floodhazard.service.LocationService;
import ws.floodhazard.service.PrecipitationConditionService;
import ws.floodhazard.service.RainTypeService;
import ws.floodhazard.util.Constants;

@Slf4j
@RestController
public class UserController {

    @Autowired
    RainTypeService rainTypeService;
    @Autowired
    LocationService locationService;
    @Autowired
    PrecipitationConditionService precipitationConditionService;


    @GetMapping(Constants.INIT)
    public String initialize() {
        log.info("Start to initialize static data");
        locationService.initializeLocations();
        precipitationConditionService.initializeSensitivityConditions();
        return rainTypeService.initializeRainType();
    }

}
