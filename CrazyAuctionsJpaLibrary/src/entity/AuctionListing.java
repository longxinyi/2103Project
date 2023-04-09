/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author chiaangyong
 */
@Entity
public class AuctionListing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionListingId;
    private String auctionName;
    private BigDecimal reservePrice;
    private String startDateTime;
    private boolean active;
    
    @OneToMany
    private List<AuctionListingBid> auctionListingBids;
    
    @OneToOne
    private Address address;
    
    @ManyToOne
    private Customer customer;
    

    public AuctionListing() {
    }

    public AuctionListing(String auctionName, BigDecimal reservePrice, String startDateTime, boolean active) {
        this.auctionName = auctionName;
        this.reservePrice = reservePrice;
        this.startDateTime = startDateTime;
        this.active = active;
    }
    

    public Long getAuctionListingId() {
        return auctionListingId;
    }

    public void setAuctionListingId(Long auctionListingId) {
        this.auctionListingId = auctionListingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auctionListingId != null ? auctionListingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the auctionListingId fields are not set
        if (!(object instanceof AuctionListing)) {
            return false;
        }
        AuctionListing other = (AuctionListing) object;
        if ((this.auctionListingId == null && other.auctionListingId != null) || (this.auctionListingId != null && !this.auctionListingId.equals(other.auctionListingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AuctionListing[ id=" + auctionListingId + " ]";
    }

    /**
     * @return the reservePrice
     */
    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    /**
     * @param reservePrice the reservePrice to set
     */
    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    /**
     * @return the startDateTime
     */
    public String getStartDateTime() {
        return startDateTime;
    }

    /**
     * @param startDateTime the startDateTime to set
     */
    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * @return the auctionName
     */
    public String getAuctionName() {
        return auctionName;
    }

    /**
     * @param auctionName the auctionName to set
     */
    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    /**
     * @return the auctionListingBid
     */
    public List<AuctionListingBid> getAuctionListingBids() {
        return auctionListingBids;
    }

    /**
     * @param auctionListingBid the auctionListingBid to set
     */
    public void setAuctionListingBids(List<AuctionListingBid> auctionListingBids) {
        this.auctionListingBids = auctionListingBids;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }
    
}
