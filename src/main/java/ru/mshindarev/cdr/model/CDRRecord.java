package ru.mshindarev.cdr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CDRRecord {
    String callType;
    String phoneNumber;
    long startTime;
    long endTime;

    @Override
    public String toString() {
        return callType + ','
                + phoneNumber + ','
                + startTime + ','
                + endTime + '\n';
    }
}
