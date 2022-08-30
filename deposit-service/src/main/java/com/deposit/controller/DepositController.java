package com.deposit.controller;

import com.deposit.controller.dto.DepositRequestDTO;
import com.deposit.controller.dto.DepositResponseDTO;
import com.deposit.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposits")
    public DepositResponseDTO depositResponseDTO(@RequestBody DepositRequestDTO requestDTO) {

        return depositService.deposit(requestDTO.getAccountId(), requestDTO.getBillId(), requestDTO.getAmount());
    }
}
