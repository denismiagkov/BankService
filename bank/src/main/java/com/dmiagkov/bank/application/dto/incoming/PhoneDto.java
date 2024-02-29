package com.dmiagkov.bank.application.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDto {
    @JsonProperty("phone_number")
    private String phoneNumber;
}
