package lk.sparkx.ncms.model;


public enum SeverityLevel {
    MINOR,
    MAJOR,
    CRITICAL;

    public String getName() {
        return this.name();
    }
}
