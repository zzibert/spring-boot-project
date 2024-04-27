package com.zzibert.accounts.service.impl;

import com.zzibert.accounts.dto.CustomerDto;
import com.zzibert.accounts.repository.AccountRepository;
import com.zzibert.accounts.repository.CustomerRepository;
import com.zzibert.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {

    }
}
