package com.amhzing.participant.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.data.mongodb")
public class MongoProperties {

    private String database;
    private String host;
    private int port;
    private String events;
    private String snapshots;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(final String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(final String events) {
        this.events = events;
    }

    public String getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(final String snapshots) {
        this.snapshots = snapshots;
    }
}
