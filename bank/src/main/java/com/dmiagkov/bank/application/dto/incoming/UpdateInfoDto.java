package com.dmiagkov.bank.application.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInfoDto {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("phone_to_add")
    private String phoneToAdd;

    @JsonSetter("phone_to_remove")
    private String phoneToRemove;

    @JsonProperty("email_to_add")
    private String emailToAdd;

    @JsonProperty("email_to_remove")
    private String emailToRemove;
}
