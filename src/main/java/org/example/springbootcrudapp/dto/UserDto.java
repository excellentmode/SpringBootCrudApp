package org.example.springbootcrudapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String password;
    private AccountDto accountData;
    private Set<EmailDataDto> emails;
    private Set<PhoneDataDto> phones;
}