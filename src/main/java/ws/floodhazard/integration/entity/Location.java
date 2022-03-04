package ws.floodhazard.integration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate"},
        allowGetters = true)
@Table(name = "LOCATION"
//        , schema = "tb"
 )
public class Location implements Serializable {

    private static final long serialVersionUID = 4774178808991641080L;

    @Column(name = "LOCATION_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOCATION_NAME")
    private String name;

    @Column(name = "LOCATION_DEV_STATUS")
    private String status;

    @Column(name = "LOCATION_DETAILS")
    private String details;

    @Column(name = "PRCPT_LEVEL1")
    private double levelOne;

    @Column(name = "PRCPT_LEVEL2")
    private double levelTwo;

    @Column(name = "PRCPT_LEVEL3")
    private double levelThree;

    @Column(name = "PRCPT_LEVEL4")
    private double levelFour;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;

}
