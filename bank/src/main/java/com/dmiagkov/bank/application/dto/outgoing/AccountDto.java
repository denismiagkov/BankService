package com.dmiagkov.bank.application.dto.outgoing;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Сущность денежного счета")
@JsonPropertyOrder({"name", "surname", "number", "balance"})
@Data
public class AccountDto {
    @Schema(description = "Идентификатор счета")
    Long id;
    @Schema(description = "Идентификатор пользователя")
    Long userId;
    @Schema(description = "Номер счета")
    String number;
    @Schema(description = "Текущий баланс")
    BigDecimal balance;
}
