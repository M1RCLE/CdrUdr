package ru.mshindarev.CDR;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.time.*;
import java.util.List;

@AllArgsConstructor
public class DatabaseWriterService implements CdrService {

    private final TransactionDataAccess transactionDataAccess;
    private final AccountDataAccess accountDataAccess;
    private final EntityManager em;

    @Override
    public void writeCDRs(CDR cdrs) {
        if (cdrs.getCdrRecords().isEmpty()) return;

        em.getTransaction().begin();
        for(CDRRecord record : cdrs.getCdrRecords()) {
            var transaction = new Transaction();
            transaction.setAccountID(accountDataAccess.findAccountByPhoneNumber(record.getPhoneNumber()));
            var offSet = ZoneId.systemDefault().getRules().getOffset(Instant.ofEpochSecond(record.startTime));
            transaction.setCallBeginTime(LocalDateTime.ofEpochSecond((int)record.startTime, 0, offSet).toLocalDate());
            transaction.setCallEndTime(LocalDateTime.ofEpochSecond((int)record.endTime, 0, offSet).toLocalDate());
            transaction.setCallType(record.callType);
            transactionDataAccess.create(transaction);
        }
        em.getTransaction().commit();
    }
}
