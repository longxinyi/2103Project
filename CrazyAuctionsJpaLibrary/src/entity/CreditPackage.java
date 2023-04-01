/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author xinyi
 */
@Entity
public class CreditPackage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditPackageId;
    private BigDecimal creditPrice;
    private String creditPackageType;

    @ManyToOne (optional = false)
    private Transaction transaction;

    public CreditPackage() {
    }

    public CreditPackage(BigDecimal creditPrice, String creditPackageType) {
        this.creditPrice = creditPrice;
        this.creditPackageType = creditPackageType;
    }
    

    public Long getCreditPackageId() {
        return creditPackageId;
    }

    public void setCreditPackageId(Long creditPackageId) {
        this.creditPackageId = creditPackageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCreditPackageId() != null ? getCreditPackageId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditPackage)) {
            return false;
        }
        CreditPackage other = (CreditPackage) object;
        if ((this.getCreditPackageId() == null && other.getCreditPackageId() != null) || (this.getCreditPackageId() != null && !this.creditPackageId.equals(other.creditPackageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CreditPackage[ id=" + getCreditPackageId() + " ]";
    }

    /**
     * @return the creditPrice
     */
    public BigDecimal getCreditPrice() {
        return creditPrice;
    }

    /**
     * @param creditPrice the creditPrice to set
     */
    public void setCreditPrice(BigDecimal creditPrice) {
        this.creditPrice = creditPrice;
    }

    /**
     * @return the creditPackageType
     */
    public String getCreditPackageType() {
        return creditPackageType;
    }

    /**
     * @param creditPackageType the creditPackageType to set
     */
    public void setCreditPackageType(String creditPackageType) {
        this.creditPackageType = creditPackageType;
    }
    
}
