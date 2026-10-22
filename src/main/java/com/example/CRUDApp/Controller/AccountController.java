package com.example.CRUDApp.Controller;


import com.example.CRUDApp.Model.Account;
import com.example.CRUDApp.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor

@RequestMapping(value = "/api/account")
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountDetails(@PathVariable("id") long id){
        return accountService.getAccountDetails(id);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account){
        return accountService.createAccount(account);
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<?> updateEmail(@PathVariable("id") long id,@RequestBody Map<String, String> body){
        return accountService.updateEmail(id,body);
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<?> updateName(@PathVariable("id") long id,@RequestBody Map<String, String> body){
        return accountService.updateName(id,body);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("id") long id,@RequestBody Map<String, String> body){
        return accountService.updateStatus(id,body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") long id){
        return accountService.deleteAccount(id);
    }


}
