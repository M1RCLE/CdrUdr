package ru.mshindarev;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.flywaydb.core.Flyway;
import ru.mshindarev.CDR.AccountDataAccess;
import ru.mshindarev.CDR.CDRCommutatorEmulatorService;

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

        var cdrCommutatorEmulatorService = new CDRCommutatorEmulatorService(
                new AccountDataAccess(emf.createEntityManager()),
                "CDR_File.txt");

        cdrCommutatorEmulatorService.generateCDRFile(Math.abs(new Random().nextInt(16))+10);
    }
}