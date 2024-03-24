package ru.mshindarev;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.flywaydb.core.Flyway;
import ru.mshindarev.CDR.*;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Random;

public class Main {
    private static EntityManagerFactory emf;
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("my-database");

        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres")
                .locations("filesystem:sql")
                .load();

        flyway.migrate();

        var em = emf.createEntityManager();
//        var cdrCommutatorEmulatorService = new CDRCommutatorEmulatorService(
//                new AccountDataAccess(em),
//                new TransactionDataAccess(em),
//                "CDRs/CDR_File");
        var writeCDRService = new FileWriteService(Path.of("./CDRs"));

        var generator = new CDRGenerator(new AccountDataAccess(em));

        LocalDate startDate = LocalDate.now().minusMonths(11);
        for(int i=0; i<12; i++) {
            LocalDate date = startDate.plusMonths(i);
            var cdrs = generator.generate(LocalDate.of(date.getYear(), date.getMonth(), 1));
            writeCDRService.writeCDRs(cdrs);
        }
    }
}