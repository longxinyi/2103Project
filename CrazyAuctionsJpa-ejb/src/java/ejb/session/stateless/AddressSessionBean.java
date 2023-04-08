/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Address;
import entity.Customer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import util.exception.AddressNotFoundException;

/**
 *
 * @author xinyi
 */
@Stateless
public class AddressSessionBean implements AddressSessionBeanRemote, AddressSessionBeanLocal {
    
    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    public Long createAddress(String addressName, Customer customer){
        Address address = new Address(addressName, false, true, customer);
        em.persist(address);
        return address.getAddressId();
    }
 
    
    public void deleteAddress(String addressName) throws AddressNotFoundException {
        Query query = em.createQuery("SELECT a from Address a WHERE a.addressName = :inAddressName");
        query.setParameter("inAddressName", addressName);
        Address address;
        
        try{
            address = (Address) query.getSingleResult();
            
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AddressNotFoundException("Address " + addressName + " does not exist!");
        }
        
        if(address.isAssociated() == false){
            em.remove(address);
            System.out.println("Address deleted successfully!");
        } else {
            address.setEnabled(false);
            System.out.println("Address is associated with a winning bid, thus has been disabled");
        }
    }

    
}
