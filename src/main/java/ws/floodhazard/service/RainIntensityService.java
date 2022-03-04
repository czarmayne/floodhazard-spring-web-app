package ws.floodhazard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ws.floodhazard.integration.entity.RainIntensity;

import java.util.Date;
import java.util.List;

public interface RainIntensityService {

    Page<RainIntensity> getRainIntensityByDate(Date date, Pageable pageable);
    Page<RainIntensity> getRainIntensity(Pageable pageable);
    String addRainIntensity(RainIntensity rainIntensity);
    List<RainIntensity> createRainIntensityInitData(Date date);
    List<RainIntensity> getAllRainIntensity();
    void deleteIntensity(String date, String hour);
    void forecastRain(String date);
    List<String> getListDates();
}
