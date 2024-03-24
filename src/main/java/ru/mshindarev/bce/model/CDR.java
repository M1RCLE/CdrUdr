package ru.mshindarev.bce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

@Getter
@AllArgsConstructor
public class CDR {
    private LocalDate month;
    private List<CDRRecord> cdrRecords;
}
