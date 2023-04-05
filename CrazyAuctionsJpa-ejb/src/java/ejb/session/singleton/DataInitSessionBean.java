/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CustomerUsernameExistException;

/**
 *
 * @author xinyi
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;
    
    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    public DataInitSessionBean()
    {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PostConstruct
    public void postConstruct(){
        
        if(em.find(Customer.class, 1l) == null){
            
            customerSessionBeanLocal.createNewCustomer("alice", "tan", new BigDecimal(0), 2, 999, "email", "alice", "password");
            //Long customerId = customerSessionBeanLocal.createNewCustomer(customer);
            
            //String firstName, String lastName, BigDecimal creditBalance, int postalCode, int contactNumber, String emailAddress, String username, String password

       }
    }

}
