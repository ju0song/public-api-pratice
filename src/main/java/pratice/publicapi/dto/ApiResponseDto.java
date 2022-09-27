package pratice.publicapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponseDto {

    private final String ENTRPS;
    private final String PRODUCT;
    private final String SRV_USE;

    public ApiResponseDto(String ENTRPS, String PRODUCT, String SRV_USE) {
        this.ENTRPS = ENTRPS;
        this.PRODUCT = PRODUCT;
        this.SRV_USE = SRV_USE;
    }
}
