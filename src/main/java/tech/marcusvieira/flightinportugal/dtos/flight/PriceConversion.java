package tech.marcusvieira.flightinportugal.dtos.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = PriceConversion.PriceConversionBuilder.class)
public class PriceConversion {

    @JsonProperty("EUR")
    private BigDecimal EUR;
}
