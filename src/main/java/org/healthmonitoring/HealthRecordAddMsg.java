package org.healthmonitoring;

public class HealthRecordAddMsg implements java.io.Serializable {
    public HealthRecord record;
    HealthRecordAddMsg(HealthRecord e) {
        this.record = e;
    }
}
