package com.bill.controller.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
public class BillRequestDTO {
    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnabled;
}
