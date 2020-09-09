package lk.sparkx.ncms.model;

public enum SeverityLevel {
    RECOVERED,
    MINOR,
    MAJOR,
    CRITICAL;

    public String getName() {
        return this.name();
    }
}
