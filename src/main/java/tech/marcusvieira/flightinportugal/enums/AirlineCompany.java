package tech.marcusvieira.flightinportugal.enums;

import lombok.Getter;

@Getter
public enum AirlineCompany {

    TAP("TP"),
    RYANAIR("FR");

    private String iataCode;

    AirlineCompany(String iataCode) {
        this.iataCode = iataCode;
    }
}
