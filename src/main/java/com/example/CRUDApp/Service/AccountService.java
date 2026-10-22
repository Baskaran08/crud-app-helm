package com.example.CRUDApp.Service;

import com.example.CRUDApp.Exception.AccountInactiveException;
import com.example.CRUDApp.Exception.EmailAlreadyUsedException;
import com.example.CRUDApp.Exception.AccountNotFoundException;
import com.example.CRUDApp.Model.Account;
import com.example.CRUDApp.Model.AccountStatus;
import com.example.CRUDApp.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public ResponseEntity<?> getAccountDetails(long id) {
        Optional<Account> existingAccount=accountRepository.findById(id);
        if(existingAccount.isEmpty()){
            throw new AccountNotFoundException("Account not found for this id");
        }
        return ResponseEntity.ok(existingAccount);
    }

    public ResponseEntity<?> createAccount(Account account) {
        if(accountRepository.existsByEmail(account.getEmail())){
            throw  new EmailAlreadyUsedException("This Email is already used by another account!");
        }
        account.setStatus(AccountStatus.ACTIVE);
        Account createdAccount=accountRepository.save(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateEmail(long id, Map<String, String> body) {
        String email=body.get("email");
        Optional<Account> existingAccount=accountRepository.findById(id);
        if(existingAccount.isEmpty()){
            throw new AccountNotFoundException("Account not found for this id");
        }
        if(existingAccount.get().getStatus()==AccountStatus.INACTIVE){
            throw new AccountInactiveException("Account is Inactive");
        }
        existingAccount.get().setEmail(email);
        Account updatedAccount = accountRepository.save(existingAccount.get());
        return ResponseEntity.ok(updatedAccount);
    }

    public ResponseEntity<?> updateStatus(long id, Map<String, String> body) {
        String status=body.get("status");
        Optional<Account> existingAccount=accountRepository.findById(id);
        if(existingAccount.isEmpty()){
            throw new AccountNotFoundException("Account not found for this id");
        }
        try {
            AccountStatus accountStatus = AccountStatus.valueOf(status.toUpperCase());
            existingAccount.get().setStatus(accountStatus);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid status. Allowed values: ACTIVE, INACTIVE");
        }
        Account updatedAccount = accountRepository.save(existingAccount.get());
        return ResponseEntity.ok(updatedAccount);
    }

    public ResponseEntity<?> updateName(long id, Map<String, String> body) {
        String name=body.get("name");
        Optional<Account> existingAccount=accountRepository.findById(id);
        if(existingAccount.isEmpty()){
            throw new AccountNotFoundException("Account not found for this id");
        }
        if(existingAccount.get().getStatus()==AccountStatus.INACTIVE){
            throw new AccountInactiveException("Account is Inactive");
        }
        existingAccount.get().setHolderName(name);
        Account updatedAccount = accountRepository.save(existingAccount.get());
        return ResponseEntity.ok(updatedAccount);
    }

    public ResponseEntity<?> deleteAccount(long id) {

        if(!accountRepository.existsById(id)){
            throw new AccountNotFoundException("Account not found for this id");
        }
        accountRepository.deleteById(id);

        return ResponseEntity.ok("Account Deleted Successfully!");
    }
}
