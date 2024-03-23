package re.mshindarev;

import re.mshindarev.CDR.CDRCommutatorEmulatorService;

public class Main {
    public static void main(String[] args) {
        var cdrCommutatorEmulatorService = new CDRCommutatorEmulatorService("CDR_File.txt");
        cdrCommutatorEmulatorService.generateCDRFile(10);
    }
}