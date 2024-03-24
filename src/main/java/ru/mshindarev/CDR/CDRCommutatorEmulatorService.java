package ru.mshindarev.CDR;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

@Setter
@AllArgsConstructor
public class CDRCommutatorEmulatorService {

    private static final Random random = new Random();

    private final AccountDataAccess accountDataAccess;
    private final TransactionDataAccess transactionDataAccess;
    private String fileNameForWriting;
    private static long transactionID = 0;

    private String generatePhoneNumber() {
        long valuesAmount = accountDataAccess.dataAmount();
        return accountDataAccess.findAccountById(abs(random.nextLong(valuesAmount)) + 1).getAccountPhoneNumber();
    }

    private long generateUnixTime(long monthAgo) {
        ZonedDateTime now = ZonedDateTime.now();
        return now.minusMonths(1+abs(random.nextLong(Math.max(1,abs(monthAgo))))).toEpochSecond();
    }

    private long generateUnixEndTime(long startTime) {
        return startTime + (long) (Math.random() * 3600); // Длительность звонка до 1 часа
    }

    private void clearFile(int monthIndex) {
        try {
            new FileWriter(fileNameForWriting + '_' + monthIndex + ".txt", false).close();
        } catch (IOException e) {
            System.err.println("CDRCommutatorEmulator::writeCDRToFile file clear error");
        }
    }

    private void writeCDRToFile(List<CDR> cdrs) {
        try {
            for (int i = 0; i < cdrs.size(); ++i) {
                FileWriter writer = new FileWriter(fileNameForWriting + "_" + (i + 1) + ".txt", true);
                for (int j = 0; j < cdrs.get(i).getCdrRecords().size(); ++j) {
                    writer.write(cdrs.get(i).getCdrRecords().get(j).toString());
                }
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("CDRCommutatorEmulator::writeCDRToFile file writing error");
        }
    }

//    private void writeCDRToDatabase(List<CDR> cdrs) {
//        for (int i = 0; i < cdrs.size(); ++i) {
//            for (int j = 0; j < cdrs.get(i).getCdrRecords().size(); ++j) {
//
//                Transaction transaction = new Transaction();
//                transaction.setId(transactionID++);
//                transaction.setAccountID(accountDataAccess.
//                        getAccoutnByPhoneNumber(
//                                cdrs.get(i).getCdrRecords().get(j).phoneNumber));
//            }
//        }
//    }



//    public List<CDR> generateCDRs(int amountOfRecords) {
//        List<CDR> returner = new LinkedList<>();
//        for (int j = 0; j < 12; ++j) {
//            clearFile(j);
//            List<CDRRecord> records = new ArrayList<>();
//            for (int i = 0; i < amountOfRecords; ++i) {
//                String callType = (i % 2 == 0) ? "02" : "01";
//                String phoneNumber = generatePhoneNumber();
//                long startTime = generateUnixTime(j);
//                long endTime = generateUnixEndTime(startTime);
//                records.add(new CDRRecord(callType, phoneNumber, startTime, endTime));
//            }
//            returner.add(new CDR(records));
//        }
//        System.out.println("CDRs успешно сгенерированы");
//        return returner;
//    }

}