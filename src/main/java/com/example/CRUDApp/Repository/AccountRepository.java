package com.example.CRUDApp.Repository;

import com.example.CRUDApp.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    public Boolean existsByEmail(String email);

}
