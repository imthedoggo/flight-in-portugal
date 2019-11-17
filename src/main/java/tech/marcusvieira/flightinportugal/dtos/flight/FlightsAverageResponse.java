package tech.marcusvieira.flightinportugal.dtos.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FlightsAverageResponse implements Serializable {

    @JsonProperty("avg_price")
    private BigDecimal avgPrice;
    @JsonProperty("bags_avg_price")
    private BagResponse bagsAvgPrice;
    @JsonProperty("currency_by_eur")
    private BigDecimal currencyByEUR;
    @JsonProperty("airport_name_from")
    private String airportNameFrom;
    @JsonProperty("airport_name_to")
    private String airportNameTo;
}
