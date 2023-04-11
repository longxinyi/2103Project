/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Address;
import entity.AuctionListing;
import entity.Customer;
import javax.ejb.Remote;
import util.exception.AddressNotFoundException;
import util.exception.ListingNotFoundException;
import util.exception.ImposterWinnerException;

/**
 *
 * @author xinyi
 */
@Remote
public interface AddressSessionBeanRemote {
    public Address createNewAddress(Address address);
    public void deleteAddress(String addressName) throws AddressNotFoundException;
    public Address createAddress(String addressName);
    public void selectAddressForWinningBid(String addressName, Customer customer, String wonListing) throws AddressNotFoundException, ListingNotFoundException, ImposterWinnerException;
}
