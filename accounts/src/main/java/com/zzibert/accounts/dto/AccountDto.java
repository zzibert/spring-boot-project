package com.zzibert.accounts.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {
  @NotEmpty(message = "AccountNumber can not be empty")
  @Pattern(regexp = "^($|[0-9]{10})", message = "Account number must be 10 digits")
  private Long accountNumber;

  @NotEmpty
  private String accountType;

  @NotEmpty
  private String branchAddress;
}
