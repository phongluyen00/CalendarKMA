package com.example.retrofitrxjava.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventModel {
    private String strDate;
    private String strStartTime;
    private String strEndTime;
    private String strName;
    private String address;
    private int image = -1;

    public EventModel(String strDate, String strStartTime, String strEndTime, String strName, String address) {
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strName = strName;
        this.address = address;
    }

    public EventModel(String strDate, String strStartTime, String strEndTime, String strName, int image) {
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strName = strName;
        this.image = image;
    }

}
