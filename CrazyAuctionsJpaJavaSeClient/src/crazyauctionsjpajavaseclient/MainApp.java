/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpajavaseclient;

import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.AuctionListing;
import entity.Customer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotFoundException;

/**
 *
 * @author xinyi
 */
public class MainApp {
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;
    private CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;
    Customer currentCustomer;

    public MainApp() {
    }

    public MainApp(AuctionListingSessionBeanRemote auctionListingSessionBeanRemote, CreditPackageSessionBeanRemote creditPackageSessionBeanRemote, CustomerSessionBeanRemote customerSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.creditPackageSessionBeanRemote = creditPackageSessionBeanRemote;
        this.auctionListingSessionBeanRemote = auctionListingSessionBeanRemote;
    }
    
    
    
    public void runApp() throws ListingNotFoundException{
        Scanner scanner = new Scanner(System.in);
        Integer response;
        
        while(true)
        {
            System.out.println("*** Welcome to Crazy AUctions Premium Customer ***\n");
            System.out.println("1: Login");
            System.out.println("2: Logout\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1)
                {
                    
                    login();
                       
                    menuApp();
               
                } else if (response == 2)
                {
                    break;
                }
                else
                {
                    System.out.print("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2)
            {
                break;
            }
        }
    
    }
    
    public void menuApp() throws ListingNotFoundException
    {
        Scanner scanner = new Scanner(System.in);
        Integer response;
        
        while(true)
        {
            System.out.println("*** Welcome to Crazy AUctions Premium Customer ***\n");
            System.out.println("1: View Credit Balance");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: Configure Proxy Bidding for Auction Listing");
            System.out.println("4: Configure Sniping for Auction Listing");
            System.out.println("5: Browse All Auction Listings");
            System.out.println("6: View Won Auction Listings");
            System.out.println("7: Logout\n");
            response = 0;
            
            while(response < 1 || response > 7)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1)
                {
                    viewCreditBalance();
                }
                else if (response == 2)
                {
                    viewAuctionListingDetails();
                    
                } else if (response == 3){
                    
                    proxyBidding();
                
                } else if (response == 4) {
                
                    sniping();
                    
                } else if (response == 5) {
               
                    browseAllAuctionListings();
                    
                } else if (response == 6) {
                
                    viewWonAuctionListings();
                    
                } else if (response == 7)
                {
                    break;
                }
                else
                {
                    System.out.print("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 7)
            {
                break;
            }
        }
    }
    
    private void login(){
        Scanner scanner = new Scanner(System.in);
           
        System.out.print("Enter username> ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        String password = scanner.nextLine().trim();
        
        try {
            currentCustomer = customerSessionBeanRemote.customerLogin(username, password);
        } catch (InvalidLoginCredentialException e){
            System.out.println("Invalid login credentials!");
        }
    }
    
    private void viewCreditBalance(){
        
        
        try{
            BigDecimal creditBalance = customerSessionBeanRemote.viewCreditBalance(currentCustomer.getUsername());
            System.out.println("Remaining Balance: " + creditBalance);
        } catch (CustomerNotFoundException e){
            System.out.println("There was an error, please try again!");
        }
    }
    
    private void viewAuctionListingDetails() throws ListingNotFoundException{
        Scanner scanner = new Scanner(System.in);
           
        System.out.print("Enter auction name> ");
        String auctionName = scanner.nextLine().trim();
        
        AuctionListing auctionListing = auctionListingSessionBeanRemote.findListingByName(auctionName);
        
        System.out.println("Name of listing : " + auctionListing.getAuctionName() + " with the current highest bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size()) + " \n");
        
    }
    
    private void proxyBidding(){}
    
    private void sniping(){}
    
    private void browseAllAuctionListings(){
        try {
            List<AuctionListing> auctionListings = auctionListingSessionBeanRemote.retrieveAuctionListing();

            for (AuctionListing auctionListing : auctionListings) {
                if (auctionListing.isActive() == true) {
                    System.out.println("Auction listing : " + auctionListing.getAuctionName() + " with reserve price of: " + auctionListing.getReservePrice() + "\n");
                }
            }

        } catch (ListingNotFoundException ex) {
            System.out.println("An error has occurred! Please try again! \n");
        }
    }
    
    private void viewWonAuctionListings(){
        List<AuctionListing> wonListings = customerSessionBeanRemote.browseWonAuctionListings(currentCustomer);

        if (wonListings.size() == 0) {
            System.out.println("You have won 0 listings.");
        } else {
            for (AuctionListing auctionListing : wonListings) {
                System.out.println("Name of listing won: " + auctionListing.getAuctionName() + " with the bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size()) + " \n");
            }

        }
    }
            
        
    
}
