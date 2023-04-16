/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListing;
import entity.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.CustomerType;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;
//import util.exception.CustomerNotFoundException;
//import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author chiaangyong
 */
@Local
public interface CustomerSessionBeanLocal {
    
    public Long createNewCustomer(Customer customer) throws CustomerUsernameExistException;
    
    public Customer createNewCustomer(String firstName, String lastName, BigDecimal creditBalance, int postalCode, int contactNumber, String emailAddress, String username, String password, CustomerType customerType);

    public void updateCustomerProfile(Customer customer) throws CustomerNotFoundException, UpdateCustomerException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;
    
    public Long updateCreditBalance(String username, BigDecimal topup) throws CustomerNotFoundException;
    
    public List<AuctionListing> browseWonAuctionListings(String customerUsername) throws CustomerNotFoundException;
    
    public void registerPremium(String customerUsername) throws CustomerNotFoundException;
    
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;
    
    public BigDecimal viewCreditBalance(String username) throws CustomerNotFoundException;

//    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;
//    
//    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;
}
