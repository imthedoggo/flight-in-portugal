# Flight in Portugal API Error Codes

Learn more about our API errors codes and how to resolve them.

Flight in Portugal uses HTTP response status codes to indicate the success or failure of your API requests.

In general, there are three status code ranges you can expect:

- 2xx success status codes confirm that your request worked as expected.
- 4xx error status codes indicate an error because of the information provided (e.g., a required parameter was omitted).
- 5xx error status codes are rare and indicate an error with Flight in Portugal servers.

Below is a list of possible error codes that can be returned, along with additional information about how to resolve them:

### data_not_found

Information not found, please try to request the data again with new filters. 

### unknown_error

Please contact development team with information on 'how to reproduce this error'. Thank you for your help and support.

### request_parameter_required

Parameter is required for requesting the data, please provide a value for it.

### third_party_provider_error

Our API uses external third-party providers to request flights and airport data, and sometimes these services are unavailable. Please try the request again in some minutes, if the problem occurs again please contact our development team. Thank you for your help and support.  

### invalid_itinerary

The itinerary provided is invalid. Below the instructions to provide a valid itinerary:
- Fly valid options are "LIS" (Lisbon) and "OPO" (Oporto).
- Fly from and fly to must be different values.

### invalid_currency

The currency provided is invalid. Below the valid currency options:

AED AFN ALL AMD ANG AOA ARS AUD AWG AZN BAM BBD BDT BGN BHD BIF BMD BND BOB BRL BSD BTC BTN BWP BYR BZD CAD CDF CHF CLF CLP CNY COP CRC CUC CUP CVE CZK DJF DKK DOP DZD EEK EGP ERN ETB EUR FJD FKP GBP GEL GGP GHS GIP GMD GNF GTQ GYD HKD HNL HRK HTG HUF IDR ILS IMP INR IQD IRR ISK JEP JMD JOD JPY KES KGS KHR KMF KPW KRW KWD KYD KZT LAK LBP LKR LRD LSL LTL LVL LYD MAD MDL MGA MKD MMK MNT MOP MRO MTL MUR MVR MWK MXN MYR MZN NAD NGN NIO NOK NPR NZD OMR PAB PEN PGK PHP PKR PLN PYG QAR QUN RON RSD RUB RWF SAR SBD SCR SDG SEK SGD SHP SLL SOS SRD STD SVC SYP SZL THB TJS TMT TND TOP TRY TTD TWD TZS UAH UGX USD UYU UZS VEF VND VUV WST XAF XAG XAU XCD XDR XOF XPD XPF XPT YER ZAR ZMK ZMW ZWL

### invalid_date

The date provided is invalid. Below the instructions to provide a valid date:
- Date format must be DD/MM/YYY (e.g. 10/11/2020).
- Date must be equal or higher (limit of 3 years in future) than current date.
- Date from must be higher than date to.