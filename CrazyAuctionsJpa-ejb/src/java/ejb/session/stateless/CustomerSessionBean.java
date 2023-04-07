/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import entity.AuctionListing;
import entity.Customer;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author chiaangyong
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    
    @Override 
    public Long createNewCustomer(Customer customer) throws CustomerUsernameExistException
    {
        
        em.persist(customer);
        
        return customer.getCustomerId();
        
    }
    
    @Override
    public Customer createNewCustomer(String firstName, String lastName, BigDecimal creditBalance, int postalCode, int contactNumber, String emailAddress, String username, String password){
        Customer customer = new Customer(firstName, lastName, creditBalance, postalCode, contactNumber, emailAddress, username, password);
        em.persist(customer);
        return customer;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Customer customer = retrieveCustomerByUsername(username);
            
            if(customer.getPassword().equals(password))
            {                
                return customer;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(CustomerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (Customer)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist! Please Register!");
        }
    }
    
    @Override
    public Long updateCreditBalance(String username, BigDecimal topup) throws CustomerNotFoundException
    {
        Customer currentCustomer = retrieveCustomerByUsername(username);
        BigDecimal currentBalance = currentCustomer.getCreditBalance();
        currentBalance = currentBalance.add(topup);
        currentCustomer.setCreditBalance(currentBalance);
        em.persist(currentCustomer);
        return currentCustomer.getCustomerId(); //not done yet
    }

   
    
    
    
}
