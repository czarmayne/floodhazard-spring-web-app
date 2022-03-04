package ws.floodhazard.integration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate"},
        allowGetters = true)
@Table(name = "RAIN_TYPE"
//        , schema = "tb"
)
public class RainType implements Serializable {

    private static final long serialVersionUID = 4151406222930939893L;

    @Id
    @Column(name = "RAIN_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MINIMUM_PRECIPITATION_RATE")
    private double minimumPrecipitationRate;

    @Column(name = "MAXIMUM_PRECIPITATION_RATE")
    private double maximumPrecipitationRate;

    @Column(name = "RAIN_LEVEL")
    private String rainLevel;

    @Column(name = "RAIN_TYPE_DETAIL")
    private String rainTypeDetail;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;

}
