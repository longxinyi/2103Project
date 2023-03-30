/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author xinyi
 */
@Entity
public class AuctionListingBid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auctionListingBidId;

    public Long getAuctionListingBidId() {
        return auctionListingBidId;
    }

    public void setBidId(Long auctionListingBidId) {
        this.auctionListingBidId = auctionListingBidId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auctionListingBidId != null ? auctionListingBidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionListingBid)) {
            return false;
        }
        AuctionListingBid other = (AuctionListingBid) object;
        if ((this.auctionListingBidId == null && other.auctionListingBidId != null) || (this.auctionListingBidId != null && !this.auctionListingBidId.equals(other.auctionListingBidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AuctionListingBid[ id=" + auctionListingBidId + " ]";
    }
    
}
