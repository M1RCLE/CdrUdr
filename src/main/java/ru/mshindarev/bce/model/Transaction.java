package ru.mshindarev.bce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @Column(name = "TransactionID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "AccountID")
    private Account accountID;

    @Column(name = "CallType")
    private String callType;

    @Column(name = "BeginTime")
    private LocalDate callBeginTime;

    @Column(name = "EndTime")
    private LocalDate callEndTime;
}
