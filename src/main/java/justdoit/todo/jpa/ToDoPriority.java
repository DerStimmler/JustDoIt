package justdoit.todo.jpa;

public enum ToDoPriority {
    URGENT, IMPORTANT, UNIMPORTANT;

    public String getLabel() {
        switch (this) {
            case URGENT:
                return "Eilig";
            case IMPORTANT:
                return "Wichtig";
            case UNIMPORTANT:
                return "Unwichtig";
            default:
                return this.toString();
        }
    }
}
