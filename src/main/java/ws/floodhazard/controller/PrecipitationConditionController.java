package ws.floodhazard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ws.floodhazard.integration.entity.SensitivityCondition;
import ws.floodhazard.service.PrecipitationConditionService;
import ws.floodhazard.util.Constants;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class PrecipitationConditionController {

    @Autowired
    private PrecipitationConditionService service;

    @PostMapping(
            consumes =
                    {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces =
                    {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addLocation(@RequestBody SensitivityCondition request){
        return service.addPrecipitationCondition(request);
    }

    @GetMapping(Constants.SENSITIVITY_CONDITION+Constants.VIEW)
    public List<SensitivityCondition> getAllPrecipitationConditionWS() {
        return service.getAllPrecipitationCondition();
    }

    @GetMapping(Constants.ADMIN+Constants.SENSITIVITY_CONDITION+Constants.VIEW)
    public ModelAndView getAllPrecipitationCondition() {
        return defaultRainTypeHandler();
    }

    @PostMapping(Constants.ADMIN+Constants.SENSITIVITY_CONDITION)
    public ModelAndView addPrecipitationCondition(@RequestBody SensitivityCondition request){
        log.debug("REDIRECT TO ADD RAIN TYPE {}", request.toString());
        service.addPrecipitationCondition(request);
        return defaultRainTypeHandler();
    }

    @DeleteMapping(Constants.ADMIN+Constants.SENSITIVITY_CONDITION+"/{id}")
    public ModelAndView deletePrecipitationCondition(@PathVariable String id){
        log.debug("REDIRECT TO DELETE PRECIPITATION CONDITION WITH ID {}", id);
        service.deleteSensitivityCondition(id);
        return defaultRainTypeHandler();
    }

    private ModelAndView defaultRainTypeHandler() {
        List<SensitivityCondition> listPrecipitationCondition = service.getAllPrecipitationCondition();
        log.debug("REDIRECT TO VIEW ALL PRECIPITATION CONDITION ================================ \n {}", Arrays.deepToString(listPrecipitationCondition.toArray()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listPrecipitationCondition", listPrecipitationCondition);
        modelAndView.setViewName("admin"+Constants.SENSITIVITY_CONDITION);
        return modelAndView;
    }
}
