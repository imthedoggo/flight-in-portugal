package tech.marcusvieira.flightinportugal.dtos.airport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = AirportData.AirportDataBuilder.class)
public class AirportData {

    private List<Location> locations;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AirportDataBuilder {
    }
}
