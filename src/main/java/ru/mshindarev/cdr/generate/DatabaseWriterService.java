package ru.mshindarev.cdr.generate;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import ru.mshindarev.cdr.model.CDR;
import ru.mshindarev.cdr.model.CDRRecord;
import ru.mshindarev.cdr.model.Transaction;

import java.time.*;

@AllArgsConstructor
public class DatabaseWriterService implements CdrService {

    private final TransactionDataAccess transactionDataAccess;
    private final AccountDataAccess accountDataAccess;
    private final EntityManager em;

    @Override
    public void writeCDRs(CDR cdr) {
        if (cdr.getCdrRecords().isEmpty()) return;

        em.getTransaction().begin();
        for(CDRRecord record : cdr.getCdrRecords()) {
            var transaction = new Transaction();
            transaction.setAccountID(accountDataAccess.findAccountByPhoneNumber(record.getPhoneNumber()));
            var offSet = ZoneId.systemDefault().getRules().getOffset(Instant.ofEpochSecond(record.getStartTime()));
            transaction.setCallBeginTime(LocalDateTime.ofEpochSecond((int)record.getStartTime(), 0, offSet).toLocalDate());
            transaction.setCallEndTime(LocalDateTime.ofEpochSecond((int)record.getEndTime(), 0, offSet).toLocalDate());
            transaction.setCallType(record.getCallType());
            transactionDataAccess.create(transaction);
        }
        em.getTransaction().commit();
    }
}
