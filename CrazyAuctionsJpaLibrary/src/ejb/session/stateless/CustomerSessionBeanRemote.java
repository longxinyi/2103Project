/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.math.BigDecimal;
import javax.ejb.Remote;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author chiaangyong
 */
@Remote
public interface CustomerSessionBeanRemote {

    public Customer createNewCustomer(String firstName, String lastName, BigDecimal creditBalance, int postalCode, int contactNumber, String emailAddress, String username, String password);

    public Long createNewCustomer(Customer customer) throws CustomerUsernameExistException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public void updateCustomerProfile(Customer customer) throws CustomerNotFoundException, UpdateCustomerException;

    
    public Long updateCreditBalance(String username, BigDecimal topup) throws CustomerNotFoundException;

}
