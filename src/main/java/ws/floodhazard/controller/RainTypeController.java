package ws.floodhazard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ws.floodhazard.integration.entity.RainType;
import ws.floodhazard.service.RainTypeService;
import ws.floodhazard.util.Constants;
import ws.floodhazard.util.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class RainTypeController {

    @Autowired
    private RainTypeService service;

    @GetMapping(Constants.RAIN_TYPE+Constants.VIEW)
    public List<RainType> getAllRainTypesWS() {
        return service.getAllRainType();
    }

    @GetMapping(Constants.ADMIN+Constants.RAIN_TYPE+Constants.VIEW)
    public ModelAndView getAllRainTypes() {
        return defaultRainTypeHandler();
    }

    @PostMapping(Constants.ADMIN+Constants.RAIN_TYPE)
    public ModelAndView addRainType(@RequestBody RainType request){
        log.debug("REDIRECT TO ADD RAIN TYPE {}", request.toString());
        service.addRainType(request);
        return defaultRainTypeHandler();
    }

    @DeleteMapping(Constants.ADMIN+Constants.RAIN_TYPE+"/{id}")
    public ModelAndView deleteRainType(@PathVariable String id){
        log.debug("REDIRECT TO DELETE RAIN TYPE WITH ID {}", id);
        service.deleteRainType(id);
        return defaultRainTypeHandler();
    }

    private ModelAndView defaultRainTypeHandler() {
        List<RainType> listOfRainTypes = service.getAllRainType();
        log.debug("REDIRECT TO VIEW ALL RAIN TYPE ================================ \n {}", Arrays.deepToString(listOfRainTypes.toArray()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listRainTypes", listOfRainTypes);
        modelAndView.setViewName("admin"+Constants.RAIN_TYPE);
        return modelAndView;
    }
}
