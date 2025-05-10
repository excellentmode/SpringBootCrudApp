package org.example.springbootcrudapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 500)
    private String password;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private AccountData accountData;

    @OneToMany(mappedBy = "userData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmailData> emails;

    @OneToMany(mappedBy = "userData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PhoneData> phones;
}