package ws.floodhazard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ws.floodhazard.integration.entity.Sensitivity;
import ws.floodhazard.integration.entity.SensitivityDTO;
import ws.floodhazard.service.FloodForecastService;
import ws.floodhazard.util.Constants;
import ws.floodhazard.util.DateUtils;
import ws.floodhazard.util.PageWrapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class FloodForecastController {

    @Autowired
    private FloodForecastService service;

    @GetMapping(Constants.ADMIN+Constants.FORECAST)
    public ModelAndView getForecast(@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                    @RequestParam("pageSize") Optional<Integer> pageSize,
                                    @RequestParam("page") Optional<Integer> page) {

        log.debug("GET FORECAST \n Date {}, PageSize {}, Page {}", date, pageSize, page);
        boolean isViewAll = false;
        if(ObjectUtils.isEmpty(date)) {
            isViewAll = true;
            date = new Date();
        }
        return pageFloodForecast(date, pageSize, page, isViewAll);

    }

    /**
     * Will be used as a web service that will serve as a basis for the notification
     * @param date
     * @return
     */
    @GetMapping(Constants.FORECAST+Constants.WARNINGS)
    public List<SensitivityDTO> getAllForeCast(@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return service.getFloodForecast(date);
    }

    @PostMapping(
            value = {Constants.FORECAST, Constants.ADMIN+Constants.FORECAST})
    public String addFloodForecast(@RequestBody Sensitivity request){
        log.debug("ADD FLOOD FORECAST {}", request);
        return service.addRainForecast(request);
    }

    @DeleteMapping(value = {Constants.ADMIN+Constants.FORECAST+"/{date}/{hour}/{location}"})
    public void delete(@PathVariable String date, @PathVariable String hour, @PathVariable String location) {
        log.debug("DELETE INTENSITY \n DATE {}, HOUR {}", date, hour);
        service.deleteForecast(date, hour, location);
    }
    private ModelAndView pageFloodForecast(Date date, Optional<Integer> pageSize, Optional<Integer> page, boolean isViewAll) {
        ModelAndView modelAndView = new ModelAndView();
        String url = Constants.ADMIN+Constants.FORECAST;
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(Constants.INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? Constants.INITIAL_PAGE : page.get() - 1;
        Page<Sensitivity> floodForecast = service.getFloodForecastByDate(date, PageRequest.of(evalPage, evalPageSize, Sort.Direction.ASC,new String[]{"date","hour"}));

        if(isViewAll) {
            floodForecast = ObjectUtils.isEmpty(date) ? service.getFloodForecast(PageRequest.of(evalPage, evalPageSize, Sort.Direction.DESC, new String[]{"date", "hour"})) : floodForecast;
        }

        PageWrapper pager = new PageWrapper(floodForecast.getTotalPages(), floodForecast.getNumber(), Constants.BUTTONS_TO_SHOW);
        url = ObjectUtils.isEmpty(date) ? url : url.concat("?date="+DateUtils.getDate(date));
        log.debug("URL ============ {}", url);
        modelAndView.addObject("floodForecast", floodForecast);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", Constants.PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("url", url);
        modelAndView.setViewName("admin/forecast");
        return modelAndView;
    }
}
