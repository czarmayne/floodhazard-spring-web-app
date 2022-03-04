package ws.floodhazard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ws.floodhazard.integration.entity.Sensitivity;
import ws.floodhazard.integration.entity.SensitivityDTO;

import java.util.Date;
import java.util.List;

public interface FloodForecastService {

    /**
     * Add the precipitation per hour for the day
     *
     * @param item
     * @return
     */
    String addRainForecast(Sensitivity item);

    /**
     * Get the rain forecast for the day; data that will be used for the notification WS
     *
     * @return
     */
    List<Sensitivity> getFloodForecast();

    List<SensitivityDTO> getFloodForecast(Date date);

    Page<Sensitivity> getFloodForecastByDate(Date date, Pageable pageable);

    Page<Sensitivity> getFloodForecast(Pageable pageable);

    void deleteForecast(String date, String hour, String location);

}