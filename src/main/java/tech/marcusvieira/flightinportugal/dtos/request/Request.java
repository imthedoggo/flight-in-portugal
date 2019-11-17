package tech.marcusvieira.flightinportugal.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {

    @JsonProperty("fly_from")
    private String flyFrom;
    @JsonProperty("fly_to")
    private String flyTo;
    @JsonProperty("date_from")
    private String dateFrom;
    @JsonProperty("date_to")
    private String dateTo;
    private String currency;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
