package tech.marcusvieira.flightinportugal.dtos.flight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = Flight.FlightBuilder.class)
public class Flight {

    private BigDecimal price;
    private Map<String, BigDecimal> conversion;
    @JsonProperty("bags_price")
    private Map<String, BigDecimal> bagsPrice;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FlightBuilder {
    }
}
