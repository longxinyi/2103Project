/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListing;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ListingNotFoundException;


/**
 *
 * @author xinyi
 */
@Stateless
public class AuctionListingSessionBean implements AuctionListingSessionBeanRemote, AuctionListingSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    
    public Long createNewListing(AuctionListing auctionListing){
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
    
    
    

}
