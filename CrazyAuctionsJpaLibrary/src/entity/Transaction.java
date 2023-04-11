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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author xinyi
 */
@Entity
public class Transaction implements Serializable {
//?? ask if this class is required
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column(nullable = false, precision = 18, scale = 4)
    @NotNull
    @Digits(integer = 14, fraction = 4)
    private BigDecimal transactionAmount;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date timeOfTransaction;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer quantity;

    @OneToOne
    private CreditPackage creditPackage;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    @OneToOne
    private AuctionListingBid refund;
    
    @OneToOne
    private AuctionListingBid bid;
    
    private Long creditPackageQuantity;

    public Transaction() {
    }
    
    public Transaction(Long creditPackageQuantity){
        this.creditPackageQuantity = creditPackageQuantity;
    }
    

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Transaction[ id=" + transactionId + " ]";
    }

    /**
     * @return the transactionAmount
     */
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount the transactionAmount to set
     */
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * @return the creditPackageQuantity
     */
    public Long getCreditPackageQuantity() {
        return creditPackageQuantity;
    }

    /**
     * @param creditPackageQuantity the creditPackageQuantity to set
     */
    public void setCreditPackageQuantity(Long creditPackageQuantity) {
        this.creditPackageQuantity = creditPackageQuantity;
    }
    
}
