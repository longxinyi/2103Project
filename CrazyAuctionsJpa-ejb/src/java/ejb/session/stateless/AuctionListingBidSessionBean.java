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
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import static oracle.jrockit.jfr.events.Bits.doubleValue;
import util.exception.BidIncrementException;
import util.exception.InvalidBidIncrementException;
import util.exception.ListingNotActiveException;
import util.exception.ListingNotFoundException;
import util.exception.LowBalanceException;
import util.exception.MinimumBidException;
import util.exception.NoBidException;

/**
 *
 * @author xinyi
 */
@Stateless
public class AuctionListingBidSessionBean implements AuctionListingBidSessionBeanRemote, AuctionListingBidSessionBeanLocal {

    @EJB
    private AuctionListingSessionBeanLocal auctionListingSessionBeanLocal;

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public AuctionListingBid createNewBid(BigDecimal price){
        AuctionListingBid newBid = new AuctionListingBid(price);
        em.persist(newBid);
        return newBid;
    }
    
    public void placeNewBid(String auctionName, BigDecimal price, String customerUsername) throws MinimumBidException, BidIncrementException, InvalidBidIncrementException, ListingNotFoundException, ListingNotActiveException, NoBidException, LowBalanceException {
        AuctionListingBid newBid = createNewBid(price);
        Query query = em.createQuery("SELECT l FROM AuctionListing l WHERE l.auctionName = :inAuctionName");
        query.setParameter("inAuctionName", auctionName);
        Query query2 = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inUsername");
        query2.setParameter("inUsername", customerUsername);
        Customer currentCustomer = (Customer) query2.getSingleResult();
        
        AuctionListing currentListing;
        try{
            currentListing = (AuctionListing) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex){
            throw new ListingNotFoundException("Listing not found, please try again");
        }
        
        if (currentListing.isActive() == false){
            throw new ListingNotActiveException("Listing Not Active, please try again!");
        }
        
        List<AuctionListingBid> listingBids = currentListing.getAuctionListingBids();
        BigDecimal balance = currentCustomer.getCreditBalance();
        
        if (balance.compareTo(new BigDecimal(0)) == 0){
            //System.out.println("BALANCE COMPARE TO 0" + balance);
            throw new LowBalanceException("You have no credits at the moment, please buy credit packages!");
        }
        if (listingBids.size() == 0){
            if (price.compareTo(new BigDecimal(0.01)) == 1 | price.compareTo(new BigDecimal(0.01)) == 0){
                if (balance.compareTo(price) >= 0){
                    listingBids.add(newBid);
                } else {
                    throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                }
            } else {
                throw new MinimumBidException("Minimum Bid is 0.0100");
            
            }
           
        } else {
            AuctionListingBid winningBid = auctionListingSessionBeanLocal.getHighestBid(auctionName);
            BigDecimal winningBidPrice = winningBid.getBidPrice();
            if (winningBidPrice.doubleValue() >= 0.01 && winningBidPrice.doubleValue() <= 0.99){
                if (price.doubleValue() >= 0.05 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 0.05, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 4.99 && winningBid.getBidPrice().doubleValue() >= 1.00){
                if (price.doubleValue() >= 0.25 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 0.25, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 24.99 && winningBid.getBidPrice().doubleValue() >= 5.00){
                if (price.doubleValue() >= 0.50 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 0.50, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 99.99 && winningBid.getBidPrice().doubleValue() >= 25.00){
                if (price.doubleValue() >= 1.00 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 1.00, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 249.99 && winningBid.getBidPrice().doubleValue() >= 100.00){
                if (price.doubleValue() >= 2.50 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 2.50, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 499.99 && winningBid.getBidPrice().doubleValue() >= 250.00){
                if (price.doubleValue() >= 5.00 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 5.00, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 999.99 && winningBid.getBidPrice().doubleValue() >= 500.00){
                if (price.doubleValue() >= 10.00 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 10.00, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 2499.99 && winningBid.getBidPrice().doubleValue() >= 1000.00){
                if (price.doubleValue() >= 25.00 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 25.00, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() <= 4999.99 && winningBid.getBidPrice().doubleValue() >= 2500.00) {
                if (price.doubleValue() >= 50.00 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 50.00, please try again!");
                }
            } else if (winningBid.getBidPrice().doubleValue() >= 5000){
                if (price.doubleValue() >= 100.00 + winningBid.getBidPrice().doubleValue()) {
                    if (balance.compareTo(price) >= 0) {
                        listingBids.add(newBid);
                    } else {
                        throw new LowBalanceException("You don't have enough credits at the moment, please buy more credit packages or lower your bid!");
                    }
                } else {
                    throw new BidIncrementException("Minimum Bid Increment is 100.00, please try again!");
                }
            } else {
                throw new InvalidBidIncrementException("Invalid bid! Please try again!");
            }
        }
        
        BigDecimal newBalance = currentCustomer.getCreditBalance().subtract(price);
        currentCustomer.setCreditBalance(newBalance);
        newBid.setCustomer(currentCustomer);
    }

    
    

}