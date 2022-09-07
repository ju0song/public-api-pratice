package pratice.publicapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Api {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ENTRPS;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String PRDUCT;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String SRV_USE;


}