package re.mshindarev.CDR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public enum CallType {
    IncomingCall("01"),
    OutComingCall("02");

    private String callType;

    @Override
    public String toString() {
        return callType;
    }
}
