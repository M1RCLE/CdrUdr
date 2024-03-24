package ru.mshindarev.cdr.generate;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.mshindarev.cdr.model.CDR;
import ru.mshindarev.cdr.model.CDRRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@AllArgsConstructor
public class FileWriteService implements CdrService {
    public final String CDR_PREFIX = "CDR_FILE_";

    private final Path path;
    @Override
    @SneakyThrows
    public void writeCDRs(CDR cdr) {
        Path filePath = Path.of(path.toString(), CDR_PREFIX + cdr.getMonth().getYear() + "_" + cdr.getMonth().getMonthValue() + ".txt");
        try (var writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for(CDRRecord record : cdr.getCdrRecords()) {
                writer.write(record.toString());
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("CDRCommutatorEmulator::writeCDRToFile file writing error");
            throw e;
        }
    }
}
