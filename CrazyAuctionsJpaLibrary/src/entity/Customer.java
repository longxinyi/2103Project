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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author xinyi
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CustomerId;
    private String firstName;
    private String lastName;
    private List<Address> listOfAddresses;
    private BigDecimal creditBalance;
    private int postalCode;
    private int contactNumber;
    private String emailAddress;
    private String username;
    private String password;
    //private String paymentDetails
    
    @ManyToOne
    private AuctionListing auctionListing;
           

    public Customer() {
    }

    public Customer(String firstName, String lastName, BigDecimal creditBalance, int postalCode, int contactNumber, String emailAddress, String username, String password, AuctionListing auctionListing) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.listOfAddresses = new ArrayList<Address>();
        this.creditBalance = creditBalance;
        this.postalCode = postalCode;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.auctionListing = auctionListing;
    }



    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long CustomerId) {
        this.CustomerId = CustomerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCustomerId() != null ? getCustomerId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.getCustomerId() == null && other.getCustomerId() != null) || (this.getCustomerId() != null && !this.CustomerId.equals(other.CustomerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + getCustomerId() + " ]";
    }


    /**
     * @return the creditBalance
     */
    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    /**
     * @param creditBalance the creditBalance to set
     */
    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the listOfAddresses
     */
    public List<Address> getListOfAddresses() {
        return listOfAddresses;
    }

    /**
     * @param listOfAddresses the listOfAddresses to set
     */
    public void setListOfAddresses(List<Address> listOfAddresses) {
        this.listOfAddresses = listOfAddresses;
    }

    /**
     * @return the postalCode
     */
    public int getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the contactNumber
     */
    public int getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the auctionListing
     */
    public AuctionListing getAuctionListing() {
        return auctionListing;
    }

    /**
     * @param auctionListing the auctionListing to set
     */
    public void setAuctionListing(AuctionListing auctionListing) {
        this.auctionListing = auctionListing;
    }

    
}
