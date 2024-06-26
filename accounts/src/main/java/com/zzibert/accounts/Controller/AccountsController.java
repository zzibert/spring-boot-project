package com.zzibert.accounts.Controller;

import com.zzibert.accounts.constants.AccountsConstants;
import com.zzibert.accounts.dto.AccountsContactInfoDto;
import com.zzibert.accounts.dto.CustomerDto;
import com.zzibert.accounts.dto.ResponseDto;
import com.zzibert.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {


  private IAccountsService iAccountsService;

  public AccountsController(IAccountsService iAccountsService) {
    this.iAccountsService = iAccountsService;
  }

  @Value("${build.version}")
  private String buildVersion;

  @Autowired
  private Environment environment;

  @Autowired
  private AccountsContactInfoDto accountsContactInfoDto;

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    iAccountsService.createAccount(customerDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
  }

  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccountDetails(
    @RequestParam
    @Pattern(regexp = "^($|[0-9]{10})", message = "Mobile number must be 10 digits")
    String mobileNumber) {
    CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(customerDto);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
    boolean isUpdated = iAccountsService.updateAccount(customerDto);
    if (isUpdated) {
      return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccount(
    @RequestParam
    @Pattern(regexp = "^($|[0-9]{10})", message = "Mobile number must be 10 digits")
    String mobileNumber) {
    boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ResponseDto(AccountsConstants.MESSAGE_417_DELETE, AccountsConstants.MESSAGE_417_DELETE));
    }
  }

  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(buildVersion);
  }

  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(environment.getProperty("JAVA_HOME"));
  }

  @GetMapping("/contact-info")
  public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(accountsContactInfoDto);
  }

}
