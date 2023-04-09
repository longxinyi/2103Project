/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Address;
import entity.AuctionListing;
import entity.AuctionListingBid;
import entity.Customer;
import java.util.ArrayList;
import java.util.Collection;
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
    
    public Address createAddress(String addressName, Customer customer){
        Address address = new Address(addressName, false, true, customer);
        em.persist(address);
        return address;
    }
    
    
    public void selectAddressForWinningBid(String addressName, Customer customer, AuctionListing wonListing) throws AddressNotFoundException{
        //check if address input exists, if not create new one, associate with address
        Query query = em.createQuery("SELECT a from Address a WHERE a.addressName = :inAddressName");
        query.setParameter("inAddressName", addressName);
        Address address;
        
        try {
            address = (Address) query.getSingleResult();
            address.setListOfWinningAuction(new ArrayList<AuctionListing>((Collection<? extends AuctionListing>) wonListing));
        } catch(NoResultException | NonUniqueResultException ex) {
            address = createAddress(addressName, customer);
            
        }
        
        address.setAssociated(true);
        address.getListOfWinningAuction().add(wonListing);
        
        
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
