/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.exceptions;
/**
 *
 * @author Goeller
 */
public class OldPasswordIncorrectException extends RuntimeException {
    public OldPasswordIncorrectException(String message) {
            super(message);
        }
}