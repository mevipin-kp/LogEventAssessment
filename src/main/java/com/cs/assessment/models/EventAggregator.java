package com.cs.assessment.models;

/**
 * Created by vipin on 01-08-2022.
 */

public class EventAggregator {

    private final LogModel startedLog;
    private final LogModel finishedLog;
    private final String EVENTID;
    private final Long TIMETAKEN;
    private final String TYPE;
    private final String HOST;
    private final Boolean ALERT;


    public EventAggregator(LogModel startedLog, LogModel finishedLog) {
        this.startedLog = startedLog;
        this.finishedLog = finishedLog;
        this.EVENTID = startedLog.getId();
        this.TIMETAKEN = getDuration();
        this.TYPE = startedLog.getType();
        this.HOST = startedLog.getHost();
        this.ALERT=validLog();
    }

    public Boolean validLog() {
        if(this.TIMETAKEN > 4)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    public Long getDuration() {
        Long dur =  Long.parseLong(finishedLog.getTimestamp()) - Long.parseLong(startedLog.getTimestamp());
        return dur;
    }

    public LogModel getStartedLog() {
        return startedLog;
    }

    public LogModel getFinishedLog() {
        return finishedLog;
    }

    public String getEVENTID() {
        return EVENTID;
    }

    public Long getTIMETAKEN() {
        return TIMETAKEN;
    }

    public String getTYPE() {
        return TYPE;
    }

    public String getHOST() {
        return HOST;
    }

    public Boolean getALERT() {
        return ALERT;
    }
}
