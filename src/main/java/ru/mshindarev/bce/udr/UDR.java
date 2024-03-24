package ru.mshindarev.bce.udr;

import lombok.Getter;

@Getter
public class UDR {
    private final String msisdn;
    private final CallTime incomingCall = new CallTime();
    private final CallTime outgoingCall = new CallTime();

    public UDR(String msisdn) {
        this.msisdn = msisdn;
    }

    public void addTime(String callType, long time) {
        var callTime = callType.equals("01") ? incomingCall : outgoingCall;
        callTime.addTime((int)time);
    }
}
