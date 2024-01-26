package org.healthmonitoring;

import java.util.List;

public class HealthRecordsResponse implements java.io.Serializable {
    final List<HealthRecord> records;

    public HealthRecordsResponse(List<HealthRecord> events) {
        this.records = events;
    }
}
