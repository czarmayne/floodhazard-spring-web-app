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
@Table(name = "SENSITIVITY"
//        , schema = "tb"
)
public class Sensitivity implements Serializable {

    private static final long serialVersionUID = 4747458340095366405L;

    @Id
    @Column(name = "SENSITIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RAIN_INTENSITY")
    private double intensity;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "SENSITIVITY_LEVEL")
    private String sensitivityLevel;

    @Column(name = "SENSITIVITY_DETAIL")
    private String sensitivityDetail;

    @Column(name = "HOUR")
    private int hour;

    @Column(name = "DATUM")
    private String date;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;

}
