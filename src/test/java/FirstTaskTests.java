import org.junit.jupiter.api.Test;
import re.mshindarev.CDR.CDRCommutatorEmulatorService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FirstTaskTests {

    @Test
    public void testGenerateCDRFileWithZeroRecords() {
        CDRCommutatorEmulatorService service = new CDRCommutatorEmulatorService("CDR_File.txt");
        service.generateCDRFile(0);
        assertTrue(fileExists("CDR_File.txt"));
        assertEquals(0, countRecordsInFile("CDR_File.txt"));
    }

    @Test
    public void testGenerateCDRFileWithOneRecord() {
        CDRCommutatorEmulatorService service = new CDRCommutatorEmulatorService("CDR_File.txt");
        service.generateCDRFile(1);
        assertTrue(fileExists("CDR_File.txt"));
        assertEquals(1, countRecordsInFile("CDR_File.txt"));
    }

    @Test
    public void testGenerateCDRFileWithLargeNumberOfRecords() {
        CDRCommutatorEmulatorService service = new CDRCommutatorEmulatorService("CDR_File.txt");
        service.generateCDRFile(1000);
        assertTrue(fileExists("CDR_File.txt"));
        assertEquals(1000, countRecordsInFile("CDR_File.txt"));
    }

    private boolean fileExists(String fileName) {
        return new java.io.File(fileName).exists();
    }

    private int countRecordsInFile(String fileName) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("FirstTaskTests::countRecordsInFile file read error");
        }
        return count;
    }
}
