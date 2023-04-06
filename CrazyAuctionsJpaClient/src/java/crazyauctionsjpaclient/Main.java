/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaclient;

import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author xinyi
 */
public class Main {



    @EJB(name = "CreditPackageSessionBeanRemote")
    private static CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;

    @EJB(name = "CustomerSessionBeanRemote")
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    @EJB(name = "AuctionListingSessionBeanRemote")
    private static AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;
    
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(customerSessionBeanRemote, creditPackageSessionBeanRemote, auctionListingSessionBeanRemote);
        mainApp.runApp();
        
    }
    
}
