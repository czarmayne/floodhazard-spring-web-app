package ws.floodhazard.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ws.floodhazard.integration.entity.Location;
import ws.floodhazard.integration.entity.LocationDTO;
import ws.floodhazard.integration.repository.LocationRepository;
import ws.floodhazard.service.LocationService;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class LocationServiceImpl extends AbstractService<Location> implements LocationService {

    @Autowired
    private LocationRepository repository;

    @Transactional
    @Override
    public String saveLocation(Location item) {
        log.debug("SAVE LOCATION {}", item.toString());
        Optional<Location> existingItem;

        if(item.getId() != null) {
            existingItem = repository.findById(item.getId());
        } else {
            existingItem = repository.findOneByName(item.getName());
        }

        if(existingItem.isPresent()) {
            log.debug("existing item to be update {}", existingItem.get().toString());
            existingItem.get().setName(item.getName());
            existingItem.get().setStatus(item.getStatus());
            existingItem.get().setDetails(item.getDetails());
            existingItem.get().setLevelOne(item.getLevelOne());
            existingItem.get().setLevelTwo(item.getLevelTwo());
            existingItem.get().setLevelThree(item.getLevelThree());
            existingItem.get().setLevelFour(item.getLevelFour());
            return response(repository.save(existingItem.get()));
        }
        return response(repository.save(item));
    }

    @Transactional
    @Override
    public void deleteLocation(String id) {
        log.debug("DELETE LOCATION WITH ID {}", id);
        Optional<Location> existingItem = repository.findById(Long.valueOf(id));
        if(existingItem.isPresent())
            repository.deleteById(Long.valueOf(id));
    }

    @Override
    public List<Location> getAllLocation() {
       return (List<Location>) repository.findAll();
    }

    @Override
    public List<LocationDTO> getAllLocationDTO() {
        List<Location> entityList = (List<Location>) repository.findAll();
        if(!CollectionUtils.isEmpty(entityList)) {
            List<LocationDTO> dtoList = new ArrayList<>();
            entityList.stream().forEach(i -> {
                LocationDTO dto = new LocationDTO();
                BeanUtils.copyProperties(i, dto);
                log.debug("DATA COPIED \n{}", dto.toString());
                dtoList.add(dto);
            });
            return dtoList;
        }
        return null;
    }

    @Override
    public void initializeLocations() {
        log.info("Start initializing locations");
        Location blumentritt = Location.builder()
                .createDate(new Date())
                .name("Blumentritt")
                .details("Maria Clara St. to Calamba St.")
                .levelOne(7)
                .levelTwo(15)
                .levelThree(30)
                .levelFour(50)
                .build();
        Location dimasalang = Location.builder()
                .createDate(new Date())
                .name("Dimasalang Ave.")
                .details("Makiling St. to Retiro St. / Maceda St.")
                .levelOne(15)
                .levelTwo(25)
                .levelThree(30)
                .levelFour(50)
                .build();
        Location espana = Location.builder()
                .createDate(new Date())
                .name("España Blvd.")
                .details("Antipolo St. to Blumentritt")
                .levelOne(10)
                .levelTwo(17)
                .levelThree(37)
                .levelFour(50)
                .build();
        Location legarda = Location.builder()
                .createDate(new Date())
                .name("Legarda St.")
                .details("Corner Gastambide St.")
                .levelOne(15)
                .levelTwo(25)
                .levelThree(30)
                .levelFour(50)
                .build();
        Location vmapa = Location.builder()
                .createDate(new Date())
                .name("V. Mapa St.")
                .details("Guadal Canal St. to Old Sta. Mesa St.")
                .levelOne(7)
                .levelTwo(15)
                .levelThree(30)
                .levelFour(50)
                .build();
        List<Location> locations = Arrays.asList(blumentritt,dimasalang,espana,legarda,vmapa);
        repository.saveAll(locations);
        log.info("Done initializing locations");
    }


}
