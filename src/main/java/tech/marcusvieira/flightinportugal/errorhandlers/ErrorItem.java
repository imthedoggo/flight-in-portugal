package tech.marcusvieira.flightinportugal.errorhandlers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = ErrorItem.ErrorItemBuilder.class)
public class ErrorItem {

    private String code;
    private String message;
    private String info;
    private Date timestamp;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorItemBuilder {

        private Date timestamp = new Date();
    }
}
