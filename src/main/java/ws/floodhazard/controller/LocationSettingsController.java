package ws.floodhazard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ws.floodhazard.integration.entity.Location;
import ws.floodhazard.integration.entity.LocationDTO;
import ws.floodhazard.service.LocationService;
import ws.floodhazard.service.RainIntensityService;
import ws.floodhazard.util.Constants;
import ws.floodhazard.util.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class LocationSettingsController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private RainIntensityService rainIntensityService;

    @Deprecated
    public ModelAndView location(){
        log.debug("REDIRECT TO LOCATION");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/lokasyon");
        return modelAndView;
    }

    @GetMapping(value = Constants.LOCATION)
    public List<String> getLocations() {
        List<String> response = locationService.getAllLocation().stream().map(i -> i.getName()).collect(Collectors.toList());
        log.debug("WS ==== getLocations: {}", response);
        return response;
    }

    @GetMapping(value = Constants.LOCATION+Constants.VIEW)
    public List<LocationDTO> getLocationsDTO() {
        List<LocationDTO> response = locationService.getAllLocationDTO();
        log.debug("WS ==== getLocations: {}", response);
        return response;
    }

    @GetMapping(
            value = {Constants.ADMIN+Constants.LOCATION+Constants.VIEW, Constants.ADMIN+Constants.LOCATION})
    public ModelAndView getAllLocation() {
        return defaultLocationHandler();
    }

    @PostMapping(Constants.ADMIN+Constants.LOCATION)
    public ModelAndView addLocation(@RequestBody Location request){
        log.debug("REDIRECT TO ADD LOCATION {}", request.toString());
        locationService.saveLocation(request);
        List<String> dateList = rainIntensityService.getListDates();
        dateList.stream().forEach(date->rainIntensityService.forecastRain(date));
        return defaultLocationHandler();
    }

    @DeleteMapping(Constants.ADMIN+Constants.LOCATION+"/{id}")
    public ModelAndView deleteLocation(@PathVariable String id){
        log.debug("REDIRECT TO DELETE LOCATION WITH ID {}", id);
        locationService.deleteLocation(id);
        return defaultLocationHandler();
    }

    /**
     * Can add location using postman
     * @param request
     * @return
     */
    @PostMapping(
            value = "/location",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String addLocation(@RequestBody Location request, @RequestParam(value = "id", required = false) long id){
        String response = locationService.saveLocation(request);
        List<String> dateList = rainIntensityService.getListDates();
        dateList.stream().forEach(date->rainIntensityService.forecastRain(date));
        return response;
    }

    /**
     * Default landing page to location
     * @return
     */
        private ModelAndView defaultLocationHandler() {
            List<Location> listOfLocations = locationService.getAllLocation();
            log.debug("REDIRECT TO VIEW ALL LOCATION ================================ \n {}", Arrays.deepToString(listOfLocations.toArray()));
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("listLocations", listOfLocations);
            modelAndView.setViewName("admin/lokasyon");
            return modelAndView;
        }
}
