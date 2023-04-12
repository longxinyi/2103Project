/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 1, max = 32)
    private String auctionName;
    @Column(precision = 18, scale = 4)
    @DecimalMin("0.0000")
    @Digits(integer = 14, fraction = 4)
    private BigDecimal reservePrice;
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = false)
//    @NotNull
    private Date startDateTime;
//    @Temporal(TemporalType.TIMESTAMP)
//    @Future
//    @Column(nullable = false)
//    @NotNull
    private Date endDateTime;
    @Column(nullable = false)
    @NotNull
    private boolean active;
    
    
    @OneToMany
    private List<AuctionListingBid> auctionListingBids;
    
    @OneToOne
    private Address address;
    
    @ManyToOne
    private Customer customer;
    

    public AuctionListing() {
    }

    public AuctionListing(String auctionName, BigDecimal reservePrice, Date startDateTime, Date endDateTime, boolean active) {
        this.auctionName = auctionName;
        this.reservePrice = reservePrice;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.active = false;
        
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
    public Date getStartDateTime() {
        return startDateTime;
    }

    /**
     * @param startDateTime the startDateTime to set
     */
    public void setStartDateTime(Date startDateTime) {
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

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
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
     * @return the endDateTime
     */
    public Date getEndDateTime() {
        return endDateTime;
    }

    /**
     * @param endDateTime the endDateTime to set
     */
    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
    
}
