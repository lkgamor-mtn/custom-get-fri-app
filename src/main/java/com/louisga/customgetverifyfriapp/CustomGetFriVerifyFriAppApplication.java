package com.louisga.customgetverifyfriapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class CustomGetFriVerifyFriAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomGetFriVerifyFriAppApplication.class, args);
    }

}

/* ---------------------------------------------------------------------------------------------------------*/
/* ------------------------------------- VERIFY-FRI IMPLEMENTATION -----------------------------------------*/
/* ---------------------------------------------------------------------------------------------------------*/

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accountholder/verify")
class VerifyFRIRestController {

    private final VerifyFRIService verifyFRIService;

    @PostMapping
    public VerifyFRIResponse verifyFRIResponse(
            @RequestBody VerifyFRIRequest request,
            @RequestHeader(required = false) String transactionId,
            @RequestHeader(required = false, name = "x-country-code")  String xCountryCode,
            @RequestHeader(required = false, name = "x-authorization") String xAuthorization) {
        log.info("VERIFY-FRI ==> request received for TransactionId [{}] for [{}]", transactionId, xCountryCode);
        return verifyFRIService.generateVerifyFRIResponse(transactionId, request);
    }
}

@Slf4j
@Service
class VerifyFRIService {

    public VerifyFRIResponse generateVerifyFRIResponse(String transactionId, VerifyFRIRequest request) {
        log.info("VERIFY-FRI REQUEST ==> [{}]", request);
        final var response = VerifyFRIResponse.builder()
                .statusCode("0000")
                .customerId(transactionId)
                .accountholderStatusData(new VerifyFRIResponse.AccountHolderStatusData(true, List.of("SNE")))
                .build();
        log.info("VERIFY-FRI RESPONSE ==> [{}]", response);
        return response;
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class VerifyFRIRequest {

    @JsonProperty("resource")
    private String resource;

    @JsonProperty(value = "accountholderids")
    private List<String> accountHolderIds;

    @JsonProperty("extension")
    private Object extension;
}


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class VerifyFRIResponse {

    private String statusCode;
    private String customerId;
    private AccountHolderStatusData accountholderStatusData;

    @Data
    @AllArgsConstructor
    static class AccountHolderStatusData {
        private Boolean isValid;
        private List<String> extension;
    }
}



/* ---------------------------------------------------------------------------------------------------------*/
/* -------------------------------------- GET-FRI IMPLEMENTATION -------------------------------------------*/
/* ---------------------------------------------------------------------------------------------------------*/

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan/v2/getFRI")
class GetFRIRestController {

    private final GetFRIService getFRIService;

    @PostMapping
    public GetFRIResponse getFRIResponse(
            @RequestBody GetFRIRequest request,
            @RequestHeader(required = false) String transactionId) {
        log.info("GET-FRI ==> request received for TransactionId [{}]", transactionId);
        return getFRIService.generateGetFRIResponse(request);
    }
}

@Slf4j
@Service
class GetFRIService {

    public GetFRIResponse generateGetFRIResponse(GetFRIRequest request) {
        log.info("GET-FRI ==> request received [{}]", request);
        return GetFRIResponse.builder()
                .message("Request successful")
                .extension(null)
                .build();
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class GetFRIRequest {

    private String resource;
    private String accountHolderId;
    private Object extension;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class GetFRIResponse {

    private String message;
    private Object extension;
}
