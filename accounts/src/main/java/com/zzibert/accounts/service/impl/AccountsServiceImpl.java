package com.zzibert.accounts.service.impl;

import com.zzibert.accounts.constants.AccountsConstants;
import com.zzibert.accounts.dto.CustomerDto;
import com.zzibert.accounts.entity.Account;
import com.zzibert.accounts.entity.Customer;
import com.zzibert.accounts.exception.CustomerAlreadyExistException;
import com.zzibert.accounts.mapper.CustomerMapper;
import com.zzibert.accounts.repository.AccountRepository;
import com.zzibert.accounts.repository.CustomerRepository;
import com.zzibert.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Customer already registered with given mobile number " + customerDto.getMobileNumber());
        } else {
            customer.setCreatedAt(LocalDateTime.now());
            customer.setCreatedBy("YoloBoss");
            Customer savedCustomer = customerRepository.save(customer);
            accountRepository.save(createNewAccount(savedCustomer));
        }
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("YoloBoss");
        return newAccount;
    }
}
