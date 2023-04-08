/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AuctionListingBid;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author xinyi
 */
@Stateless
public class auctionListingBidSessionBean implements auctionListingBidSessionBeanRemote, auctionListingBidSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public Long createNewBid(BigDecimal price){
        AuctionListingBid newBid = new AuctionListingBid(price);
        em.persist(price);
        return newBid.getAuctionListingBidId();
    }

}
