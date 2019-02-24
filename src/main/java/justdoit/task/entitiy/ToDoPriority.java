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
