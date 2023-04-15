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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;


import util.exception.ListingNotFoundException;

import util.exception.ListingNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.NoBidException;
import util.exception.WrongDateException;


/**
 *
 * @author xinyi
 */
@Stateless
public class AuctionListingSessionBean implements AuctionListingSessionBeanRemote, AuctionListingSessionBeanLocal{

    @Resource
    private TimerService timerService;
    
    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    
    public Long createNewAuctionListing(AuctionListing auctionListing){
        em.persist(auctionListing);
        em.flush();
        TimerHandle newStartTimer = newTimer(auctionListing.getStartDateTime(), auctionListing);
        auctionListing.setTimerHandle(newStartTimer);
        
        
        return auctionListing.getAuctionListingId();
    }
    

    public AuctionListing findListingByName(String auctionName) throws ListingNotFoundException
    {
        Query query = em.createQuery("SELECT l FROM AuctionListing l WHERE l.auctionName = :inauctionName");
        query.setParameter("inauctionName", auctionName);
        
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

    public void updateAuctionListing(String auctionListingName, String newDetail, int type) throws ListingNotFoundException, ParseException, WrongDateException {
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.auctionName = :inAuctionName");
        query.setParameter("inAuctionName", auctionListingName);
        AuctionListing auctionListing;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
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
            auctionListing.setStartDateTime(format.parse(newDetail));
            
            TimerHandle newStartTimeHandle = newTimer(format.parse(newDetail), auctionListing);
            auctionListing.setTimerHandle(newStartTimeHandle);
            
        } else if (type == 4){
            
            if(auctionListing.getStartDateTime().before(format.parse(newDetail))){
                auctionListing.setEndDateTime(format.parse(newDetail));
            } else {
                throw new WrongDateException("Ending Date cannot be before Starting Date");
            }
            
        } else if (type == 5){
            auctionListing.setActive(Boolean.valueOf(newDetail));
        }
        
     
    }
    
    public void deleteAuctionListing(String auctionName) throws ListingNotFoundException
    {
        AuctionListing auctionListingToRemove;
        try {
        auctionListingToRemove = findListingByName(auctionName);
        auctionListingToRemove.getTimerHandle().getTimer().cancel();
        } catch (ListingNotFoundException e){
            throw new ListingNotFoundException("Listing does not exist!");
        }
        
        em.remove(auctionListingToRemove);
       
    }
    
    public List<AuctionListing> viewAuctionListingsBelowReservePrice() throws ListingNotFoundException, NoBidException {
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.active = FALSE ");
        List<AuctionListing> auctionListings;
        
        List<AuctionListing> belowReservePrice = new ArrayList<AuctionListing>();
        try{
            auctionListings = query.getResultList();
            for(AuctionListing auctionListing : auctionListings){
                boolean isBelowReservePrice = false;
                try {
                    AuctionListingBid highestBid = getHighestBid(auctionListing.getAuctionName());
                } catch (NoBidException e){
                    
                }
                
                List<AuctionListingBid> bids = auctionListing.getAuctionListingBids();
                for(AuctionListingBid auctionListingBid : bids){
                    if (auctionListingBid.getBidPrice().compareTo(auctionListing.getReservePrice()) == -1){
                        isBelowReservePrice = true;
                    }
                    
                }
                
                if (isBelowReservePrice == true) {
                    belowReservePrice.add(auctionListing);
                }
            }
        } catch (NoResultException e){
            System.out.println("No inactive auction listings with bids below reserve price!");
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
        
        if (size != 0) {
            AuctionListingBid winningBid = auctionListing.getAuctionListingBids().get(size - 1);
            Customer winningCustomer = winningBid.getCustomer();

            winningCustomer.getListOfWonAuctionListings().add(auctionListing);
        } else {
            
        }
        
    }
    
    public AuctionListingBid getHighestBid(String auctionName) throws ListingNotFoundException, NoBidException {
        AuctionListing auctionListing = findListingByName(auctionName);
        List<AuctionListingBid> auctionListingBids = auctionListing.getAuctionListingBids();
        int size = auctionListingBids.size();
        if (size == 0){
            throw new NoBidException("No bids made yet!");
        } else {
            AuctionListingBid highestBid = auctionListingBids.get(size - 1);
            return highestBid;
        }
        
    }
    
    public TimerHandle newTimer(Date date, AuctionListing auctionListing){
        Timer timer = timerService.createTimer(date, auctionListing);
        System.out.println("TIMER CREATED");
        return timer.getHandle();
        
    }
    
//    @Timeout
//    public void timeout(Timer timer){
//        //change active status
//        System.out.println("TIME OUT METHOD");
//        AuctionListing auctionListing = (AuctionListing) timer.getInfo();
//        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.auctionName = :inAuctionName");
//        query.setParameter("inAuctionName", auctionListing.getAuctionName());
//        AuctionListing setAuctionListing = (AuctionListing) query.getSingleResult();
//        
//        if(setAuctionListing.isActive() == false){
//            System.out.println("TIME OUT METHOD IF{} ");
//            setAuctionListing.setActive(true);
//            TimerHandle newEndTimer = newTimer(setAuctionListing.getEndDateTime(), setAuctionListing);
//            setAuctionListing.setTimerHandle(newEndTimer);
//            
//        } else {
//            //if listing is active
//            setAuctionListing.setActive(false);
//            assignWinningBid(setAuctionListing.getAuctionName());
//            int size = setAuctionListing.getAuctionListingBids().size();
//            System.out.println("NUMBER OF BIDS :" + size);
//            if (size != 0) {
//                AuctionListingBid winningBid = setAuctionListing.getAuctionListingBids().get(size - 1);
//                if (winningBid.getBidPrice().compareTo(setAuctionListing.getReservePrice()) >= 0){
//                
//                    Customer winningCustomer = winningBid.getCustomer();
//
//                    winningCustomer.getListOfWonAuctionListings().add(setAuctionListing);
//                }
//            } else {
//                System.out.println("This listing has no bids!");
//            }
//
//            
//        }
//    }
    
    @Timeout
    public void timeout(Timer timer) {
        try {
            //change active status
            System.out.println("TIME OUT METHOD");
            AuctionListing auctionListing = (AuctionListing) timer.getInfo();
            Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.auctionName = :inAuctionName");
            query.setParameter("inAuctionName", auctionListing.getAuctionName());
            AuctionListing setAuctionListing = (AuctionListing) query.getSingleResult();

            if (!setAuctionListing.isActive()) {
                System.out.println("TIME OUT METHOD IF{} ");
                setAuctionListing.setActive(true);
                TimerHandle newEndTimer = newTimer(setAuctionListing.getEndDateTime(), setAuctionListing);
                setAuctionListing.setTimerHandle(newEndTimer);
            } else {
                //if listing is active
                setAuctionListing.setActive(false);
                assignWinningBid(setAuctionListing.getAuctionName());
                int size = setAuctionListing.getAuctionListingBids().size();
                System.out.println("NUMBER OF BIDS :" + size);
                if (size != 0) {
                    AuctionListingBid winningBid = setAuctionListing.getAuctionListingBids().get(size - 1);
                    if (winningBid.getBidPrice() != null && setAuctionListing.getReservePrice() != null
                            && winningBid.getBidPrice().compareTo(setAuctionListing.getReservePrice()) >= 0) {

                        Customer winningCustomer = winningBid.getCustomer();
                        winningCustomer.getListOfWonAuctionListings().add(setAuctionListing);
                    }
                } else {
                    System.out.println("This listing has no bids!");
                }
            }
        } catch (IllegalArgumentException | TransactionRequiredException | NoResultException
                | NonUniqueResultException | IndexOutOfBoundsException | NullPointerException ex) {
            // handle any potential exceptions thrown by the methods in your code
            
        }
    }

    
//    public void assignWinningBidForListingsWBidsBelowReservePrice(String auctionName){
//        List<AuctionListing> listings = viewAuctionListingsBelowReservePrice();
//        
//    }

}
