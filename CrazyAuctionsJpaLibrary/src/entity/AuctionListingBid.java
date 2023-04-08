/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author xinyi
 */
@Entity
public class AuctionListingBid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionListingBidId;
    private BigDecimal bidPrice;
    
    @OneToMany
    private List<Transaction> listOfRefundTransaction;
    
    @OneToOne
    private Transaction bidTransaction;
    
    @OneToOne
    private Customer customer;
    
    @ManyToOne(optional = false)
    @JoinColumn
    private AuctionListing auction;

    public AuctionListingBid() {
        this.listOfRefundTransaction = new ArrayList<Transaction>();
    }

    public AuctionListingBid(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }
    
    

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

    /**
     * @return the bidPrice
     */
    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    /**
     * @param bidPrice the bidPrice to set
     */
    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }
    
}
