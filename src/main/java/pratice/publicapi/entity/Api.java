package pratice.publicapi.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Api {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=500)

    private String ENTRPS;

    @Column(length=500)
    @NotNull

    private String PRODUCT;

    @Column(length=500)
    @NonNull

    private String SRV_USE;

}