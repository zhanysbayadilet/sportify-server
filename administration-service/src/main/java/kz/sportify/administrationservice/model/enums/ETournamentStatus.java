package kz.sportify.administrationservice.model.enums;
public enum ETournamentStatus {
    OPEN("OPEN"),
    IN_PROCESS("IN_PROCESS"),
    COMPLETED("COMPLETED");

    private final String status;

    ETournamentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
