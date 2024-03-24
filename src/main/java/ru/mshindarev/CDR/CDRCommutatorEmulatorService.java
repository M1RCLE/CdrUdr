package ru.mshindarev.CDR;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static java.lang.Math.abs;

@Setter
@AllArgsConstructor
public class CDRCommutatorEmulatorService {

    private static final Random random = new Random();

    private final AccountDataAccess accountDataAccess;
    private String fileNameForWriting;
    private final long currentUnixTimeSeconds = Instant.now().getEpochSecond();

    private String generatePhoneNumber() {
        long valuesAmount = accountDataAccess.dataAmount();
        return accountDataAccess.findAccountById(abs(random.nextLong(valuesAmount))+1).getAccountPhoneNumber();
    }

    private long generateUnixTime() {
        ZonedDateTime now = ZonedDateTime.now();
        long seconds = 30 * 24 * 3600;
        return now.minusSeconds(abs(random.nextLong(seconds))).toEpochSecond();
    }

    private long generateUnixEndTime(long startTime) {
        return startTime + (long)(Math.random() * 3600); // Длительность звонка до 1 часа
    }

    private String createCDRRecord(String callType, String phoneNumber, long startTime, long endTime) {
        return callType + "," +
                phoneNumber + "," +
                startTime + "," +
                endTime + "\n";
    }

    private void writeCDRToFile(String cdrRecord) {
        try {
            FileWriter writer = new FileWriter(fileNameForWriting, true);
            writer.write(cdrRecord);
            writer.close();
        } catch (IOException e) {
            System.err.println("CDRCommutatorEmulator::writeCDRToFile file writing error");
        }
    }

    private void clearFile() {
        try {
            new FileWriter(fileNameForWriting, false).close();
        } catch (IOException e) {
            System.err.println("CDRCommutatorEmulator::writeCDRToFile file clear error");
        }
    }

    public void generateCDRFile(int amountOfRecords) {
        clearFile();
        for (int i = 0; i < amountOfRecords; ++i) {
            String callType = (i % 2 == 0) ? "02" : "01";
            String phoneNumber = generatePhoneNumber();
            long startTime = generateUnixTime();
            long endTime = generateUnixEndTime(startTime);
            String cdrRecord = createCDRRecord(callType, phoneNumber, startTime, endTime);

            if (i == 0) clearFile();
            writeCDRToFile(cdrRecord);
        }

        System.out.println("CDR файл успешно сгенерирован.");
    }
}