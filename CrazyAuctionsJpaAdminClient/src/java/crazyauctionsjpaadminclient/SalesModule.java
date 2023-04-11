/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.AuctionListingSessionBeanRemote;
import entity.AuctionListing;
import entity.Employee;
import java.util.List;
import java.util.Scanner;
import util.enumeration.AccessRightEnum;
import util.exception.ListingNotFoundException;
import util.exception.InvalidAccessRightException;
import util.exception.ListingNotFoundException;

/**
 *
 * @author xinyi
 */
public class SalesModule {
    
    private Employee currentEmployee;
    
    private AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;

    public SalesModule() {
    }

    public SalesModule(AuctionListingSessionBeanRemote auctionListingSessionBeanRemote) {
        this.auctionListingSessionBeanRemote = auctionListingSessionBeanRemote;
    }
    
    
    
    
    public void salesOperation() throws InvalidAccessRightException, ListingNotFoundException
    {
        if(currentEmployee.getAccessRightEnum() != AccessRightEnum.SALES)
        {
            throw new InvalidAccessRightException("You don't have SALES rights to access the system administration module.");
        }
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Crazy Auctions :: Sales Operation ***\n");
            System.out.println("1: Create Auction Listing");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: Update Auction Listing");
            System.out.println("4: Delete Auction Listing");
            System.out.println("5: View All Auction Listings with Bids below reserve price");
            System.out.println("6: Assign Winning Bid for Listing with Bids but below Reserve Price");
            System.out.println("7: Back\n");
            response = 0;
            
            while(response < 1 || response > 7)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                   createAuctionListing();
                }
                else if(response == 2)
                {
                    viewAuctionListingDetails();
                }
                else if (response == 3)
                {
                    updateAuctionListing();
                    
                } else if (response == 4) {
                    
                    deleteAuctionListing();
                    
                } else if (response == 5){
                    
                    viewAllAuctionListing();
                    
                } else if (response == 6) {
                    
                    assignWinningBid();
                    
                } else if (response == 7) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 7)
            {
                break;
            }
        }
        
        
    }
    

    public void createAuctionListing(){}
    
    public void viewAuctionListingDetails() throws ListingNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter auction listing name > ");
        String auctionListingName = scanner.nextLine().trim();
        AuctionListing auctionListing;
        try{
            auctionListing = auctionListingSessionBeanRemote.findListingByName(auctionListingName);
            System.out.println("Auction Listing Name: " + auctionListing.getAuctionName() + " with the current highest bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size() - 1).getBidPrice());
        } catch (ListingNotFoundException e){
            throw new ListingNotFoundException("Listing not found, please try again");
        }
    }
    public void updateAuctionListing(){}
    public void deleteAuctionListing(){}
    
    public void viewAllAuctionListing() throws ListingNotFoundException{
        try {
            List<AuctionListing> activeAuctions = auctionListingSessionBeanRemote.retrieveAuctionListing();
            for (AuctionListing auctionListing : activeAuctions) {
                System.out.println("Name of listing : " + auctionListing.getAuctionName() + " with the current highest bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size()) + " \n");
            }
        } catch (ListingNotFoundException e){
            throw new ListingNotFoundException("No Listings found!");
        }
    }
    public void assignWinningBid(){}
    
}
