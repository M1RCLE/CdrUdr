package ru.mshindarev.cdr.generate;

import lombok.AllArgsConstructor;
import ru.mshindarev.cdr.model.CDR;
import ru.mshindarev.cdr.model.CDRRecord;

import java.time.*;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

@AllArgsConstructor
public class CDRGenerator {
    private static final Random random = new Random();
    private final AccountDataAccess accountDataAccess;

    public CDR generate(LocalDate date) {
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), 1, 0, 0, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1);
        long limitTick = Instant.now().getEpochSecond();
        long startTick = startDate.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(startDate));
        if (startTick > limitTick) {
            return new CDR(startDate.toLocalDate(), Collections.emptyList());
        }
        long endTick = Math.min(endDate.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(endDate)), limitTick);
        return new CDR(
                startDate.toLocalDate(),
                IntStream.range(0, 10 + Math.abs(random.nextInt(10)))
                .mapToObj(ignore -> Instant.ofEpochSecond(startTick).plusSeconds(Math.abs(random.nextLong(endTick - startTick))))
                .map(this::generateCDRRecord)
                .toList());
    }

    private String generatePhoneNumber() {
        long valuesAmount = accountDataAccess.dataAmount();
        return accountDataAccess.findAccountById(abs(random.nextLong(valuesAmount)) + 1).getAccountPhoneNumber();
    }

    private CDRRecord generateCDRRecord(Instant instant) {
        return new CDRRecord(random.nextInt() % 2 == 0 ? "01" : "02",
                generatePhoneNumber(), instant.toEpochMilli() / 1000,
                instant.toEpochMilli() / 1000 + (10 + Math.abs(random.nextInt(900))));
    }
}
