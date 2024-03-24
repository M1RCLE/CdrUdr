package ru.mshindarev.CDR;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @Column(name = "AccountID")
    private long id;

    @Column(name = "AccountPhoneNumber")
    private String accountPhoneNumber;
}
