/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListing;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author xinyi
 */
@Stateless
public class AuctionListingSessionBean implements ListingSessionBeanRemote, ListingSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    
    public Long createNewListing(AuctionListing auctionListing){
        em.persist(auctionListing);
        em.flush();
        
        return auctionListing.getAuctionListingId();
    }
    
    
    

}
