/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListing;
import entity.AuctionListingBid;
import java.math.BigDecimal;
import javax.ejb.Remote;
import util.exception.BidIncrementException;
import util.exception.InvalidBidIncrementException;
import util.exception.ListingNotFoundException;
import util.exception.MinimumBidException;

/**
 *
 * @author xinyi
 */
@Remote
public interface AuctionListingBidSessionBeanRemote {
    public AuctionListingBid createNewBid(BigDecimal price);
    public void placeNewBid(String listingName, BigDecimal price) throws MinimumBidException, BidIncrementException, InvalidBidIncrementException, ListingNotFoundException;
}