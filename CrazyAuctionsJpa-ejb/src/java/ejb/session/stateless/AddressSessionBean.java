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
import java.util.List;
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
import util.exception.ListingNotFoundException;
import util.exception.ImposterWinnerException;

/**
 *
 * @author xinyi
 */
@Stateless
public class AddressSessionBean implements AddressSessionBeanRemote, AddressSessionBeanLocal {
    
    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    public Address createAddress(String addressName){
        Address address = new Address(addressName, false, true);
        em.persist(address);
        return address;
    }
    
    public Address createNewAddress(Address address){
        em.persist(address);
        return address;
    }
    
    public Address findAddressByName(String addressName) throws AddressNotFoundException{
        Query query = em.createQuery("SELECT a from Address a WHERE a.addressName = :inAddressName");
        query.setParameter("inAddressName", addressName);
        Address address;
        try{
            address = (Address) query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new AddressNotFoundException("Address not found, please add address!");
            
        }
        
        return address;
    }
    
    public void selectAddressForWinningBid(String addressName, String customerName, String wonListing) throws AddressNotFoundException, ListingNotFoundException, ImposterWinnerException{
        //check if address input exists, if not create new one, associate with address
        Query query1 = em.createQuery("SELECT a from Address a WHERE a.addressName = :inAddressName");
        query1.setParameter("inAddressName", addressName);
        Query query2 = em.createQuery("SELECT l FROM AuctionListing l WHERE l.auctionName = :inwonListing");
        query2.setParameter("inwonListing", wonListing);
        Query query3 = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inCustomerUsername");
        query3.setParameter("inCustomerUsername", customerName);
        List<Address> addresses;
        Address address;
        AuctionListing auctionListing;
        Customer customer;
        
        try {
            address = (Address) query1.getSingleResult();
            
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new AddressNotFoundException("Address not found, please add address!");
            
        }
        
        try {
            auctionListing = (AuctionListing) query2.getSingleResult();
            customer = (Customer) query3.getSingleResult();
            
            if(customer.getListOfWonAuctionListings().contains(auctionListing)){
                addresses = customer.getListOfAddresses();
                
                if(addresses.contains(address)){
                    List<AuctionListing> won = address.getListOfWinningAuction();
                    won.add(auctionListing);
                    address.setListOfWinningAuction(won);
                    address.setAssociated(true);
                } else {
                    throw new AddressNotFoundException("Error, address missing");
                }
            } else {
                throw new ImposterWinnerException("You did not win this listing, please try again");
            }
           
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new ListingNotFoundException("Auction Listing not found, please add address!");
            
        }
        
        try {
            address = (Address) query1.getSingleResult();
            System.out.println("asd");
            address.getListOfWinningAuction().add(auctionListing);
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new AddressNotFoundException("Address not found, please add address!");
            
        }
        
        
       
        
        
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

    public void addAddress(String addressName, String customerUsername){
        Address newAddress = new Address();
        Query query = em.createQuery("SELECT c from Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", customerUsername);
        
        Customer customer = (Customer) query.getSingleResult();
        
        try{
            findAddressByName(addressName);
            System.out.println("Address already exists!");
        } catch (AddressNotFoundException e){
            newAddress = createAddress(addressName);
            customer.getListOfAddresses().add(newAddress);
            newAddress.setCustomer(customer);
        }
    }
    
    public List<Address> viewAllAddress(String customerUsername) throws AddressNotFoundException {
        Query query2 = em.createQuery("SELECT c from Customer c WHERE c.username = :inUsername");
        query2.setParameter("inUsername", customerUsername);
        
        Customer customer = (Customer) query2.getSingleResult();
        Query query1 = em.createQuery("SELECT a FROM Address a");
        List<Address> addresses;
        
        try{
            addresses = query1.getResultList();
        } catch (NoResultException ex){
            throw new AddressNotFoundException("No addresses added!");
        }
        
        return addresses;
    }
    
    public void updateAddress(String addressName) throws AddressNotFoundException{
    
        try {
            Address address = findAddressByName(addressName);
            address.setAddressName(addressName);
        } catch(AddressNotFoundException e){
            throw new AddressNotFoundException();
        }
        
        
    }
    
}
