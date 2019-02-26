package justdoit.task.entitiy;

public enum ToDoStatus {
    OPEN, IN_PROGRESS, FINISHED, CANCELED;

    public String getLabel() {
        switch (this) {
            case OPEN:
                return "Offen";
            case IN_PROGRESS:
                return "In Bearbeitung";
            case FINISHED:
                return "Beendet";
            case CANCELED:
                return "Abgebrochen";
            default:
                return this.toString();
        }
    }

}
