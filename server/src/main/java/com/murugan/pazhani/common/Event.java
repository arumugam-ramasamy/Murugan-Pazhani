package com.murugan.pazhani.common;

//public record Event (long ts, String type, String ticker, long id, String message) {}

public class Event {
    private final long ts;
    private final String type;
    private final String ticker;
    private final long id;
    private final String message;

    public Event(long ts, String type, String ticker, long id, String message) {
        this.ts = ts;
        this.type = type;
        this.ticker = ticker;
        this.id = id;
        this.message = message;
    }

    public long ts() {
        return ts;
    }

    public String type() {
        return type;
    }

    public String ticker() {
        return ticker;
    }

    public long id() {
        return id;
    }

    public String message() {
        return message;
    }
}
