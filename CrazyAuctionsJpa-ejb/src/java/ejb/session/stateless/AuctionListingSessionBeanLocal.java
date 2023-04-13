/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListing;
import entity.AuctionListingBid;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Local;

import util.exception.ListingNotFoundException;

import util.exception.ListingNotFoundException;
import util.exception.NoBidException;
import util.exception.WrongDateException;


/**
 *
 * @author xinyi
 */
@Local
public interface AuctionListingSessionBeanLocal {
    
    public Long createNewAuctionListing(AuctionListing auctionListing);

    public AuctionListing findListingByName(String auctionName) throws ListingNotFoundException;

    public List<AuctionListing> retrieveAuctionListing() throws ListingNotFoundException;
    public List<AuctionListing> viewActiveListings() throws ListingNotFoundException;
    public void deleteAuctionListing(String auctionName) throws ListingNotFoundException;
    public List<AuctionListing> viewAuctionListingsBelowReservePrice() throws ListingNotFoundException, NoBidException;
    public void updateAuctionListing(String auctionListingName, String newDetail, int type) throws ListingNotFoundException, ParseException, WrongDateException;
    public void assignWinningBid(String auctionName);
    public AuctionListingBid getHighestBid(String auctionName) throws ListingNotFoundException, NoBidException;
}
