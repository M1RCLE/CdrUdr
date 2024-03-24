package ru.mshindarev.CDR;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @Column(name = "AccountID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AccountPhoneNumber")
    private String accountPhoneNumber;
}
