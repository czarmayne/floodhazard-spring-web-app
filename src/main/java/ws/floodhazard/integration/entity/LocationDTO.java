package ws.floodhazard.integration.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LocationDTO implements Serializable {

    private String name;
    private String details;
}
