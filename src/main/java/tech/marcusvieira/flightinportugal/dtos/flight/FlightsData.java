package tech.marcusvieira.flightinportugal.dtos.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = FlightsData.FlightsDataBuilder.class)
public class FlightsData {

    @JsonProperty("data")
    private List<Flight> flights;
}
