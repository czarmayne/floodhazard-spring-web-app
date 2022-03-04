package ws.floodhazard.service;

import ws.floodhazard.integration.entity.Location;
import ws.floodhazard.integration.entity.LocationDTO;

import java.util.List;

public interface LocationService {

    String saveLocation(Location item);
    void deleteLocation(String id);
    List<Location> getAllLocation();
    List<LocationDTO> getAllLocationDTO();
    void initializeLocations();
}

