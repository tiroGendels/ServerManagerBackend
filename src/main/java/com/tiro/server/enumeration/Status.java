package com.tiro.server.enumeration;

public enum Status {
    SERVER_DOWN("SERER_DOWN"),
    SERVER_UP("SERVER_UP"),
    UNKNOWN_HOST("UNKNOWN_HOST");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
