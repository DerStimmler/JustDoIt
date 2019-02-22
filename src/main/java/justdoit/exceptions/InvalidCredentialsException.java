/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.exceptions;

/**
 *
 * @author Lichter, Ansgar
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
            super(message);
        }
}