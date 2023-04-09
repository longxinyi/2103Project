/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListing;
import java.util.List;
import javax.ejb.Remote;

import util.exception.ListingNotFoundException;
import util.exception.AuctionListingNotFoundException;

/**
 *
 * @author xinyi
 */
@Remote
public interface AuctionListingSessionBeanRemote {
    public AuctionListing findListingByName(String auctionName) throws ListingNotFoundException;
    public List<AuctionListing> retrieveAuctionListing() throws AuctionListingNotFoundException;
    public List<AuctionListing> viewActiveListings() throws ListingNotFoundException;
            
}
