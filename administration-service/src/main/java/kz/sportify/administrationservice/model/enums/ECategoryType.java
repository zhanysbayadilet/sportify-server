package kz.sportify.administrationservice.model.enums;

public enum ECategoryType {
    TEAM("TEAM"),
    SINGLE("SINGLE");

    private final String status;

    ECategoryType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
