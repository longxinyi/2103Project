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

    /**
     * @return the listOfRefundTransaction
     */
    public List<Transaction> getListOfRefundTransaction() {
        return listOfRefundTransaction;
    }

    /**
     * @param listOfRefundTransaction the listOfRefundTransaction to set
     */
    public void setListOfRefundTransaction(List<Transaction> listOfRefundTransaction) {
        this.listOfRefundTransaction = listOfRefundTransaction;
    }

    /**
     * @return the bidTransaction
     */
    public Transaction getBidTransaction() {
        return bidTransaction;
    }

    /**
     * @param bidTransaction the bidTransaction to set
     */
    public void setBidTransaction(Transaction bidTransaction) {
        this.bidTransaction = bidTransaction;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the auction
     */
    public AuctionListing getAuction() {
        return auction;
    }

    /**
     * @param auction the auction to set
     */
    public void setAuction(AuctionListing auction) {
        this.auction = auction;
    }
    
}
