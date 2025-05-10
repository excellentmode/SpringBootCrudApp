package org.example.springbootcrudapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String password;
    private AccountModel accountData;
    private Set<EmailDataModel> emails;
    private Set<PhoneDataModel> phones;
}