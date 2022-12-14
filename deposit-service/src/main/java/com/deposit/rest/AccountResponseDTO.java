package com.deposit.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private Long accountID;

    private String name;

    private String email;

    private String phone;

    private List<Long> bills;

    private OffsetDateTime creationDate;

}
