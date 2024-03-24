package ru.mshindarev.CDR;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Getter
@AllArgsConstructor
public class CDR {
    private LocalDate month;
    private List<CDRRecord> cdrRecords;
}
