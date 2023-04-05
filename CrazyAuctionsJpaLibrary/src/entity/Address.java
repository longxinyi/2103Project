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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author chiaangyong
 */
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String addressName;
    private boolean associated;
    
    @OneToMany(mappedBy = "address")
    private AuctionListing winningAuction;
    
    @ManyToOne
    private Customer customer;

    public Long getAddressId() {
        return addressId;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getAddressId() != null ? getAddressId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the addressId fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.getAddressId() == null && other.getAddressId() != null) || (this.getAddressId() != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Address[ id=" + getAddressId() + " ]";
    }

    /**
     * @param addressId the addressId to set
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * @return the addressName
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * @param addressName the addressName to set
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * @return the associated
     */
    public boolean isAssociated() {
        return associated;
    }

    /**
     * @param associated the associated to set
     */
    public void setAssociated(boolean associated) {
        this.associated = associated;
    }
    
}
