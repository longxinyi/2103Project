/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListingBid;
import entity.Customer;
import java.math.BigDecimal;
import javax.ejb.Local;
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
@Local
public interface AuctionListingBidSessionBeanLocal {
    public AuctionListingBid createNewBid(BigDecimal price);
    public void placeNewBid(String auctionName, BigDecimal price, Customer currentCustomer) throws MinimumBidException, BidIncrementException, InvalidBidIncrementException, ListingNotFoundException, ListingNotActiveException, NoBidException, LowBalanceException;
}