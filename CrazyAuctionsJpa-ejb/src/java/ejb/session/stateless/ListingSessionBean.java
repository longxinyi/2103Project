/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Listing;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author xinyi
 */
@Stateless
public class ListingSessionBean implements ListingSessionBeanRemote, ListingSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    
    public Long createNewListing(Listing listing){
        em.persist(listing);
        em.flush();
        
        return listing.getListingId();
    }
    
    
    

}
