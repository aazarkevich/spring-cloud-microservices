package com.account.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class AccountRequesDTO {

    private Long accountID;

    private String name;

    private String email;


    private String phone;

    private OffsetDateTime creationDate;

}
