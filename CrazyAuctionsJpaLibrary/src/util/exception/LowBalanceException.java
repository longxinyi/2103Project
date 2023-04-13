/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author xinyi
 */
public class LowBalanceException extends Exception {

    public LowBalanceException() {
    }

    public LowBalanceException(String string) {
        super(string);
    }
    
}
