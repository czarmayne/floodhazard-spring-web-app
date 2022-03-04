package ws.floodhazard.integration.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SensitivityDTO {

    private String location;
    private String intensity;
    private String date;
    private int hour;
    private String sensitivityLevel;
    private String sensitivityDetail;

}
