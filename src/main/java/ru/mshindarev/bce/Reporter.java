package ru.mshindarev.bce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.mshindarev.bce.udr.UDR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class Reporter {

    private final Map<String,Map<Integer, UDR>> udrs = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .registerModule(new JavaTimeModule());

    public static void main(String[] args) throws IOException {
        var cdrPath = Path.of("CDRs");
        var reportPath = Path.of("reports");

        var processor = new Reporter();
        processor.load(cdrPath);
        processor.save(reportPath);
    }

    private void save(Path reportPath) throws IOException {
        if (!Files.exists(reportPath)) {
            Files.createDirectory(reportPath);
        }
        udrs.forEach((key, value) -> processMsisdn(reportPath, key, value));
    }

    private void processMsisdn(Path reportPath,String msisdn, Map<Integer, UDR> months) {
        months.forEach((key, value) -> processMonth(reportPath, msisdn, key, value));
    }

    @SneakyThrows
    private void processMonth(Path reportPath, String msisdn, int month, UDR value) {
        Path filePath = Path.of(reportPath.toString(), msisdn+"_"+month+".json");
        try (var writer = Files.newBufferedWriter(
                filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)
        ) {
            writer.write(objectMapper.writeValueAsString(value));
            writer.flush();
        }
    }

    private void load(Path path) throws IOException {
        try (var files = Files.list(path)) {
            files.forEach(this::processFile);
        }
    }

    private static final Pattern filePattern = Pattern.compile("CDR_FILE_(\\d+)_(\\d+)\\.txt");

    @SneakyThrows
    private void processFile(Path filePath) {
        try(var reader = Files.newBufferedReader(filePath)) {
            Matcher matcher = filePattern.matcher(filePath.getFileName().toString());
            if (matcher.matches()) {
                int month = Integer.parseInt(matcher.group(2));
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    processLine(month, line);
                }
            }
        }
    }

    private static final Pattern cdrPattern = Pattern.compile("(\\S+),(\\d+),(\\d+),(\\d+)");

    private void processLine(int month, String line) {
        Matcher matcher = cdrPattern.matcher(line);
        if (matcher.matches()) {
            String callType = matcher.group(1);
            String msisdn = matcher.group(2);
            long time = Long.parseLong(matcher.group(4)) - Long.parseLong(matcher.group(3));
            var phoneMap = udrs.computeIfAbsent(msisdn, ignore -> new HashMap<>());
            var udr = phoneMap.computeIfAbsent(month, ignore -> new UDR(msisdn));
            udr.addTime(callType, time);
        }
    }

}
