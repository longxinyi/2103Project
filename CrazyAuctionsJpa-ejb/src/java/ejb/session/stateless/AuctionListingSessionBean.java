/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListing;
import entity.AuctionListingBid;
import entity.Customer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ListingNotFoundException;

import util.exception.ListingNotFoundException;
import util.exception.CustomerNotFoundException;


/**
 *
 * @author xinyi
 */
@Stateless
public class AuctionListingSessionBean implements AuctionListingSessionBeanRemote, AuctionListingSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    
    public Long createNewAuctionListing(AuctionListing auctionListing){
        
        
       
        em.persist(auctionListing);
        em.flush();
        
        return auctionListing.getAuctionListingId();
    }
    

    public AuctionListing findListingByName(String auctionName) throws ListingNotFoundException
    {
        Query query = em.createQuery("SELECT l FROM Listing l WHERE l.auctionName = :inname");
        query.setParameter("inname", auctionName);
        
        try
        {
            return (AuctionListing)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new ListingNotFoundException("Listing: " + auctionName + " does not exist! Please try again!");
        }
    }
    

    @Override
    public List<AuctionListing> retrieveAuctionListing() throws ListingNotFoundException
    {
        Query query = em.createQuery("SELECT a FROM AuctionListing a ");
        
        try
        {
            return (List<AuctionListing>)query.getResultList();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new ListingNotFoundException("Auction Listing not ready!");
        }
    }
    
    public List<AuctionListing> viewActiveListings() throws ListingNotFoundException{
        Query query = em.createQuery("SELECT l FROM AuctionListing l WHERE l.active = :true");
        List<AuctionListing> activeAuctions;
        
        try{
            activeAuctions = query.getResultList();
        } catch (NoResultException ex){
            throw new ListingNotFoundException("No Active Listing at the moment!");
        }
        
        return activeAuctions;
    }

    public void updateAuctionListing(String auctionListingName, String newDetail, int type) throws ListingNotFoundException{
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.auctionName = :inAuctionName");
        query.setParameter("inAuctionName", auctionListingName);
        AuctionListing auctionListing;
        
        try {
            auctionListing = (AuctionListing) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new ListingNotFoundException("Listing Name: " + auctionListingName + " does not exist!");
        }
        
        if(type == 1){
            auctionListing.setAuctionName(newDetail);
        } else if (type == 2){
            auctionListing.setReservePrice(new BigDecimal(newDetail));
        } else if (type == 3){
            //auctionListing.setStartDateTime(newDetail);
        } else if (type == 4){
            //auctionListing.setEndDateTime(newDetail);
        } else if (type == 5){
            auctionListing.setActive(Boolean.valueOf(newDetail));
        }
        
     
    }
    
    public void deleteAuctionListing(String auctionName) throws ListingNotFoundException
    {
        AuctionListing auctionListingToRemove;
        try {
        auctionListingToRemove = findListingByName(auctionName);
        } catch (ListingNotFoundException e){
            throw new ListingNotFoundException("Listing does not exist!");
        }
        
        em.remove(auctionListingToRemove);
       
    }
    
    public List<AuctionListing> viewAuctionListingsBelowReservePrice(){
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.active = FALSE ");
        
        
        List<AuctionListing> auctionListings = query.getResultList();
        List<AuctionListing> belowReservePrice = new ArrayList<AuctionListing>();
        for (AuctionListing auctionListing : auctionListings){
            if (auctionListing.getAuctionListingBids().size() != 0){
                if (auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size() - 1).getBidPrice().compareTo(auctionListing.getReservePrice()) == -1 ){
                    belowReservePrice.add(auctionListing);
                }
            }
        }
        return belowReservePrice;
       
    }
    
    public void assignWinningBid(String auctionName){
    //once closed
        AuctionListing auctionListing = new AuctionListing();
        try{
            auctionListing = findListingByName(auctionName);
        } catch (ListingNotFoundException e){
            System.out.println("Listing Not Found, please try again");
        }
        
        int size = auctionListing.getAuctionListingBids().size();
        
        if (size == 0){
            auctionListing.setActive(false);
        } else {
            auctionListing.setActive(false);
            AuctionListingBid winningBid = auctionListing.getAuctionListingBids().get(size - 1);
            Customer winningCustomer = winningBid.getCustomer();

            winningCustomer.getListOfWonAuctionListings().add(auctionListing);
        }
        
    }

}
