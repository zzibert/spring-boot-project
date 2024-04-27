package com.zzibert.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Column(name="customer_id")
    private Long customerId;

    @Id
    @Column(name="account_number")
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    private String name;

    private String email;

    @Column(name="branch_address")
    private String branchAddress;
}
