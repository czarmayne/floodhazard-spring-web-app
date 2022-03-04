package ws.floodhazard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ws.floodhazard.integration.entity.RainIntensity;
import ws.floodhazard.service.RainIntensityService;
import ws.floodhazard.util.Constants;
import ws.floodhazard.util.DateUtils;
import ws.floodhazard.util.PageWrapper;

import java.util.*;

@Slf4j
@RestController
public class RainIntensityController {

    @Autowired
    private RainIntensityService service;

    @PostMapping(
            value = {Constants.RAIN_INTENSITY, Constants.ADMIN+Constants.RAIN_INTENSITY},
            consumes =
                    {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces =
                    {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addRainIntensity(@RequestBody RainIntensity request){
        log.debug("ADD INTENSITY {}", request);
        return service.addRainIntensity(request);
    }

    @PostMapping(
            value = Constants.ADMIN+Constants.RAIN_INTENSITIES,
            consumes =
                    {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces =
                    {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView addRainIntensities(@RequestBody List<RainIntensity> request){
        log.debug("ADD LIST INTENSITY {}", request);
        request.stream().forEach(rainIntensity -> service.addRainIntensity(rainIntensity));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("feedback",Constants.ADD_SUCCESS);
        modelAndView.setViewName("admin/intensity");
        return modelAndView;
    }

    @GetMapping(value = {Constants.ADMIN+Constants.RAIN_INTENSITY+Constants.VIEW, Constants.VIEW})
    public ModelAndView getAllRainIntensity(
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("page") Optional<Integer> page
    ) {
        log.debug("getAllRainIntensity");
        return pageRainIntensity(date, pageSize, page, true, "admin/rainintensities");
    }

    @GetMapping(Constants.ADMIN+Constants.RAIN_INTENSITY)
    public ModelAndView createRainIntensity(
                                            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                            @RequestParam("pageSize") Optional<Integer> pageSize,
                                            @RequestParam("page") Optional<Integer> page) {
        log.debug("REDIRECT TO CREATE INITIAL RAIN INTENSITY ================================ \n ");
        service.createRainIntensityInitData(date);
        return pageRainIntensity(date, pageSize, page, false, "admin/rainintensity");
    }

    @DeleteMapping(value = {Constants.ADMIN+Constants.RAIN_INTENSITY+"/{date}/{hour}"})
    public void deleteRainIntensity(@PathVariable String date, @PathVariable String hour) {
        log.debug("DELETE INTENSITY \n DATE {}, HOUR {}", date, hour);
        service.deleteIntensity(date, hour);
    }
    private ModelAndView pageRainIntensity(Date date, Optional<Integer> pageSize, Optional<Integer> page, boolean isViewAll, String view) {
        ModelAndView modelAndView = new ModelAndView();
        String url = Constants.ADMIN+Constants.RAIN_INTENSITY;
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(Constants.INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? Constants.INITIAL_PAGE : page.get() - 1;
        log.info("EVALUATION PAGE === {} EVALUATION PAGE SIZE ====== {}",evalPage, evalPageSize);
        Page<RainIntensity> intensitiesPaginate = null;

        if(isViewAll) {
            log.info("VIEW ALL RAIN INTENSITY");
            intensitiesPaginate = service.getRainIntensity(PageRequest.of(evalPage, evalPageSize, Sort.Direction.DESC, new String[]{"date", "hour"}));
            log.info("ALL INTENSITY {}", Arrays.deepToString(new Object[]{intensitiesPaginate}));
            url = url.concat(Constants.VIEW);
        } else {
            intensitiesPaginate = service.getRainIntensityByDate(date, PageRequest.of(evalPage, evalPageSize, Sort.Direction.ASC,new String[]{"date","hour"}));
        }

        PageWrapper pager = new PageWrapper(intensitiesPaginate.getTotalPages(), intensitiesPaginate.getNumber(), Constants.BUTTONS_TO_SHOW);
        url = ObjectUtils.isEmpty(date) ? url : url.concat("?date="+DateUtils.getDate(date));
        log.debug("URL ============ {}", url);
        modelAndView.addObject("intensities", intensitiesPaginate);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", Constants.PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("url", url);
        modelAndView.setViewName(view);
        return modelAndView;
    }
}
