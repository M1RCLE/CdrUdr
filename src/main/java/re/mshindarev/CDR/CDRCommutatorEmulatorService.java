package re.mshindarev.CDR;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

@Setter
@AllArgsConstructor
public class CDRCommutatorEmulatorService {
    private String fileNameForWriting;

    private String generatePhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("7"); // Предположим, что это российские номера
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }

    private long generateUnixTime() {
        long minUnixTime = 1577836800L;
        long maxUnixTime = System.currentTimeMillis() / 1000;
        return minUnixTime + (long) (Math.random() * (maxUnixTime - minUnixTime));
    }

    private String createCDRRecord(CallType callType, String phoneNumber, long startTime, long endTime) {
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

    private long generateUnixEndTime(long startTime) {
        return startTime + (long)(Math.random() * 3600L); // Длительность звонка до 1 часа
    }

    public void generateCDRFile(int amountOfRecords) {
        for (int i = 0; i < amountOfRecords; ++i) {
            CallType callType = CallType.valueOf((i % 2 == 0) ? "IncomingCall" : "OutComingCall");
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