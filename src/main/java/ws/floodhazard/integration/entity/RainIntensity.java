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
@Table(name = "RAIN_INTENSITY"
//        , schema = "tb"
)
public class RainIntensity implements Serializable {

    private static final long serialVersionUID = -5142983575852410756L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RAIN_INTENSITY_ID")
    private Long id;

    @Column(name = "INTENSITY")
    private double intensity;

    @Column(name = "RAIN_TYPE")
    private String type;

    @Column(name = "HOUR")
    private int hour;

    @Column(name = "DATUM")
    private String date;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;

}
