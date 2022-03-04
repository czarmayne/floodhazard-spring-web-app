package ws.floodhazard.service;


import ws.floodhazard.integration.entity.RainType;

import java.util.List;
import java.util.Optional;

public interface RainTypeService {

    String addRainType(RainType rainType);
    List<RainType> getAllRainType();
    Optional<String> getRainType(double intensity);
    void deleteRainType(String id);
    String initializeRainType();

}
