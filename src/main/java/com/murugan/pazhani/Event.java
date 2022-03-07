package com.murugan.pazhani;

public record Event (long ts, String type, String ticker, long id, String message) {}
