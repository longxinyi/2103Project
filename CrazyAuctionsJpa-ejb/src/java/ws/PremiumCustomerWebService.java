/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import ejb.session.stateless.AuctionListingSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.AuctionListing;
import entity.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotFoundException;

/**
 *
 * @author xinyi
 */
@WebService(serviceName = "PremiumCustomerWebService")
@Stateless()
public class PremiumCustomerWebService {

    @EJB
    private AuctionListingSessionBeanLocal auctionListingSessionBeanLocal;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "customerLogin")
    public Customer customerLogin(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws InvalidLoginCredentialException {
        return customerSessionBeanLocal.customerLogin(username, password);
    }
    
    @WebMethod(operationName = "viewAuctionListingDetails")
    public AuctionListing viewAuctionListingDetails(@WebParam(name = "auctionName") String auctionName) throws ListingNotFoundException {
        return auctionListingSessionBeanLocal.findListingByName(auctionName);
    }
    
    @WebMethod(operationName = "viewCreditBalance")
    public BigDecimal viewCreditBalance(@WebParam(name = "username") String username) throws CustomerNotFoundException {
        return customerSessionBeanLocal.retrieveCustomerByUsername(username).getCreditBalance();
    }
    
    @WebMethod(operationName = "configureProxyBidding")
    public void configureProxyBidding(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws InvalidLoginCredentialException {
        
    }
    
    @WebMethod(operationName = "browseAllAuctionListings")
    public List<AuctionListing> browseAllAuctionListings() throws ListingNotFoundException {
        return auctionListingSessionBeanLocal.retrieveAuctionListing();
    }
    
    @WebMethod(operationName = "viewWonAuctionListings")
    public List<AuctionListing> viewWonAuctionListings(@WebParam(name = "customer") Customer customer) throws ListingNotFoundException {
        return customerSessionBeanLocal.browseWonAuctionListings(customer);
    }
    

    


    
    
    
}
