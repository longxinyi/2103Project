/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AddressSessionBeanLocal;
import ejb.session.stateless.AuctionListingSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.Address;
import entity.AuctionListing;
import entity.Customer;
import entity.Employee;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AccessRightEnum;
import util.exception.CustomerUsernameExistException;
import util.exception.EmployeeUsernameExistException;

/**
 *
 * @author xinyi
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private AddressSessionBeanLocal addressSessionBeanLocal;

    @EJB
    private AuctionListingSessionBeanLocal auctionListingSessionBeanLocal;

    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
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
            
            
            auctionListingSessionBeanLocal.createNewAuctionListing(new AuctionListing("hello", new BigDecimal(0.5), new Date(123,4,7,8,00), new Date(123,5,7,8,00),true));
            
            //addressSessionBeanLocal.createNewAddress(new Address("home", false, true));
            
            employeeSessionBeanLocal.createNewEmployee("Prof","Lek","manager","password", AccessRightEnum.SYSTEMADMIN);
            
        
            
        }
    }

}
