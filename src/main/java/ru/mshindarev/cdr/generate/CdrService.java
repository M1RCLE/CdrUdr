package ru.mshindarev.cdr.generate;


import ru.mshindarev.cdr.model.CDR;

public interface CdrService {
    void writeCDRs(CDR cdrs);
}
