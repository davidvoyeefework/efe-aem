package com.myproject.core.models;

public class DataPoint {
    private final String id;
    private final String label;
    private final String metadata;
    private final String value;

    public DataPoint(String id, String label, String metadata, String value) {
        this.id = id;
        this.label = label;
        this.metadata = metadata;
        this.value = value;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getValue() {
        return value;
    }
}
