package ru.mshindarev.bce.udr;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class CallTime {
    private int totalTime = 0;
    public void addTime(int duration) {
        totalTime += duration;
    }

    @JsonFormat(pattern="HH:mm:ss")
    public LocalTime getTotalTime() {
        return LocalTime.of(totalTime/3600, (totalTime % 3600)/60, totalTime%60);
    }
}
