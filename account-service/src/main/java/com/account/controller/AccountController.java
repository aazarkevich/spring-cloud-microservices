package com.account.controller;

import com.account.controller.dto.AccountResponseDTO;
import com.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public AccountResponseDTO getAccount(@PathVariable Long accountId) {
        return new AccountResponseDTO(accountService.getAccountById(accountId));
    }

    @PostMapping("/")
    public Long createAccount(@RequestBody AccountResponseDTO accountResponseDTO) {
        return accountService.createAccount(accountResponseDTO.getName(), accountResponseDTO.getEmail(),
                accountResponseDTO.getPhone(), accountResponseDTO.getBills());
    }

    @PostMapping("/{accountId}")
    public AccountResponseDTO updateAccount(@PathVariable Long accountId,
            @RequestBody AccountResponseDTO accountResponseDTO) {
        return new AccountResponseDTO(accountService.updateAccount(accountId,accountResponseDTO.getName(), accountResponseDTO.getEmail(),
                accountResponseDTO.getPhone(), accountResponseDTO.getBills()));
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDTO deleteAccount(@PathVariable Long accountId) {
        return new AccountResponseDTO(accountService.deleteAccount(accountId));
    }
}
