package com.github.uuidcode.builder.request;

public enum LatencyType {
    HIGH(1_000, 1_000),
    MIDDLE(3_000, 3_000),
    LOW(10_000, 10_000),
    VERY_LOW(30_000, 30_000),
    VERY_VERY_LOW(100_000, 100_000);;

    private Integer connectionTimeout;
    private Integer socketTimeout;

    LatencyType(Integer connectionTimeout, Integer socketTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }
}