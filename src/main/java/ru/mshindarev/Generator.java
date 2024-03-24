package ru.mshindarev;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.flywaydb.core.Flyway;
import ru.mshindarev.cdr.generate.*;

import java.nio.file.Path;
import java.time.LocalDate;

public class Generator {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-database");

        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres")
                .locations("filesystem:sql")
                .load();

        flyway.migrate();

        var em = emf.createEntityManager();

        var writeCDRService = new FileWriteService(Path.of("./CDRs"));

        var accountDataAccess = new AccountDataAccess(em);
        var transactionDataAccess = new TransactionDataAccess(em);

        var databaseWriter = new DatabaseWriterService(transactionDataAccess, accountDataAccess, em);
        var generator = new CDRGenerator(accountDataAccess);

        LocalDate startDate = LocalDate.now().minusMonths(11);
        for(int i=0; i<12; i++) {
            LocalDate date = startDate.plusMonths(i);
            var cdr = generator.generate(LocalDate.of(date.getYear(), date.getMonth(), 1));
            writeCDRService.writeCDRs(cdr);
            databaseWriter.writeCDRs(cdr);
        }
    }
}