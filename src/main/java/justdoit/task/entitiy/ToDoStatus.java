/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.task.entitiy;

/**
 *
 * @author Lichter, Ansgar
 */
public enum ToDoStatus {
    OPEN, IN_PROGRESS, FINISHED, CANCELED;
    
    public String getLabel() {
        switch(this) {
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
