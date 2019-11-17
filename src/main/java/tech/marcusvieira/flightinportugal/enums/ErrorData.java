package tech.marcusvieira.flightinportugal.enums;

import lombok.Getter;

@Getter
public enum ErrorData {

    NOT_FOUND("data_not_found",
        "https://github.com/marcusvieira88/flight-in-portugal/blob/master/documentation/Errors.md#data_not_found"),
    UNKNOWN("unknown_error",
        "https://github.com/marcusvieira88/flight-in-portugal/blob/master/documentation/Errors.md#unknown_error"),
    THIRD_PARTY_PROVIDER_ERROR("third_party_provider_error",
        "https://github.com/marcusvieira88/flight-in-portugal/blob/master/documentation/Errors.md#third_party_provider_error"),
    REQUEST_PARAMETER_REQUIRED("request_parameter_required",
        "https://github.com/marcusvieira88/flight-in-portugal/blob/master/documentation/Errors.md#request_parameter_required"),
    INVALID_ITINERARY("invalid_itinerary",
        "https://github.com/marcusvieira88/flight-in-portugal/blob/master/documentation/Errors.md#invalid_itinerary"),
    INVALID_CURRENCY("invalid_currency",
        "https://github.com/marcusvieira88/flight-in-portugal/blob/master/documentation/Errors.md#invalid_currency"),
    INVALID_DATE("invalid_date",
        "https://github.com/marcusvieira88/flight-in-portugal/blob/master/documentation/Errors.md#invalid_date");

    private String code;
    private String info;

    ErrorData(String code, String info) {
        this.code = code;
        this.info = info;
    }
}
