package tech.marcusvieira.flightinportugal.dtos.flight;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BagResponse implements Serializable {

    private BigDecimal first;
    private BigDecimal second;
}
