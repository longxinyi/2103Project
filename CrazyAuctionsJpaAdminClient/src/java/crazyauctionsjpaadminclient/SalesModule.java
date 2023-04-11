/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.AuctionListingSessionBeanRemote;
import entity.AuctionListing;
import entity.Employee;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.AccessRightEnum;
import util.exception.ListingNotFoundException;
import util.exception.InvalidAccessRightException;
import util.exception.ListingExistException;
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

    public SalesModule(AuctionListingSessionBeanRemote auctionListingSessionBeanRemote, Employee currentEmployee) {
        this.auctionListingSessionBeanRemote = auctionListingSessionBeanRemote;
        this.currentEmployee = currentEmployee;
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
            System.out.println("5: View All Auction Listings");
            System.out.println("6: View All Auction Listings with Bids below reserve price");
            System.out.println("7: Assign Winning Bid for Listing with Bids but below Reserve Price");
            System.out.println("8: Back\n");
            response = 0;
            
            while(response < 1 || response > 8)
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
                    
                    viewAllAuctionListingsBelowReservePrice();
                    
                } else if (response == 7) {
                    
                    assignWinningBid();
                    
                } else if (response == 8) {
                    break;   
                } else {
                    System.out.println("Invalid option, please try again!\n");             
                }
            }
            
            if(response == 8)
            {
                break;
            }
        }
        
        
    }
    

    public void createAuctionListing() throws ListingNotFoundException{
    //not visible to customers until start date time is reached
        Scanner scanner = new Scanner(System.in);
        AuctionListing newAuctionListing = new AuctionListing();
        
        //public AuctionListing(String auctionName, BigDecimal reservePrice, Date startDateTime, Date endDateTime, boolean active)
        
        System.out.println("*** Crazy Auctions :: System Administration :: Create New Auction Listing ***\n");
        System.out.print("Enter Auction Name> ");
        String auctionName = scanner.nextLine().trim();
        newAuctionListing.setAuctionName(auctionName);
        System.out.print("Enter Reserve Price> ");
        newAuctionListing.setReservePrice(new BigDecimal(scanner.nextLine().trim()));
        System.out.print("Enter Start Date Time (YYYYMMDDHHMM) > ");
        //newAuctionListing.setStartDateTime(scanner.nextLine().trim());
        System.out.print("Enter End Date Time (YYYYMMDDHHMM) > ");
        //newAuctionListing.setEndDateTime(scanner.nextInt());        
        System.out.print("Set Active or Inactive > $");
        newAuctionListing.setActive(true);
        scanner.nextLine();
        
        
        try
        {
            auctionListingSessionBeanRemote.findListingByName(auctionName);
           
        }
        catch(ListingNotFoundException ex)
        {
            Long newAuctionListingId = auctionListingSessionBeanRemote.createNewAuctionListing(newAuctionListing);
            
            System.out.println("New auction listing created successfully!: " + newAuctionListing.getAuctionListingId() + "\n");
        }
    }
    
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
    public void updateAuctionListing(){
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        System.out.println("*** CrazyAuctions: Update Auction Listing Details ***\n");
        System.out.print("Enter name of auction listing to be updated> ");
        String auctionListingName = scanner.nextLine().trim();

       
        while (true) {
            System.out.println("*** Which part of your profile would you like to update? ***\n");
            System.out.println("1: Auction Name");
            System.out.println("2: Reserve Price");
            System.out.println("3: Start Date Time");
            System.out.println("4: End Date Time");
            System.out.println("5: Change Active Status");
            System.out.println("6: Exit\n");

            response = 0;
            while (response < 1 || response > 6) {
                Scanner newScanner = new Scanner(System.in);
                System.out.print("> ");

//                response = scanner.nextInt();
//                if (response == 1) {
//
//                    try {
//                        System.out.print("Enter New Auction Name > ");
//                        String newAuctionName = newScanner.nextLine().trim();
//                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newAuctionName, 1);
//
//                    } catch (ListingNotFoundException e) {
//                        System.out.print("There was an error, please try again ");
//                    }
//
//                    System.out.print("First Name has been successfully changed! ");
//
//                } else if (response == 2) {
//                    try {
//                        System.out.print("Enter New Reserve Price > ");
//                        String newReservePrice = newScanner.nextLine().trim();
//                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newReservePrice, 2);
//
//                    } catch (ListingNotFoundException e) {
//                        System.out.print("There was an error, please try again ");
//                    }
//
//                    System.out.print("Reserve Price has been successfully changed! ");
//
//
//                } else if (response == 3) {
//                    try {
//                        System.out.print("Enter New Start Date Time > ");
//                        String newStartDateTime = newScanner.nextLine().trim();
//                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newStartDateTime, 3);
//
//                    } catch (ListingNotFoundException e) {
//                        System.out.print("There was an error, please try again ");
//                    }
//
//                    System.out.print("Start Date Time has been successfully changed! ");
//
//
//                } else if (response == 4) {
//                     try {
//                        System.out.print("Enter New End Date Time > ");
//                        String newEndDateTime = newScanner.nextLine().trim();
//                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newEndDateTime, 4);
//
//                    } catch (ListingNotFoundException e) {
//                        System.out.print("There was an error, please try again ");
//                    }
//
//                    System.out.print("End Date Time has been successfully changed! ");
//
//                } else if (response == 5) {
//
//                    try {
//                        System.out.print("Enter New Active Status > ");
//                        String newActiveStatus = newScanner.nextLine().trim();
//                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newActiveStatus, 5);
//
//                    } catch (ListingNotFoundException e) {
//                        System.out.print("There was an error, please try again ");
//                    }
//
//                    System.out.print("Active Status has been successfully changed! ");
//
//                    
//                } else if (response == 6){
//                    break; 
//                } else {
//                    System.out.println("Invalid option, please try again!\n");
//                }
//
            }

            if (response == 6) {
                break;
            }

        }
    }
    public void deleteAuctionListing(){
        Scanner scanner = new Scanner(System.in);     
        String input;
        
        System.out.println("*** Crazy Auctions :: Sales :: View Product Details :: Delete Product ***\n");
        System.out.println("Enter Name of auction you wish to delete >");
        input = scanner.nextLine().trim();
        
        try {
            auctionListingSessionBeanRemote.deleteAuctionListing(input);
        } catch (ListingNotFoundException e){
            System.out.println("Listing not found!");
        }
    }
    
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
    
    public void viewAllAuctionListingsBelowReservePrice(){
        //filter to show only expired or closed listings
        List<AuctionListing> belowReservePrice = auctionListingSessionBeanRemote.viewAuctionListingsBelowReservePrice();
        
        if(belowReservePrice.size() == 0){
            System.out.println("No inactive/closed auctions with bids below reserve price!");
        }
        
        for(AuctionListing auctionListing : belowReservePrice){
            System.out.println("Name of Listing: " + auctionListing.getAuctionName() + "with the highest bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size()) + " \n");
        }
    
    }
    public void assignWinningBid(){
        Scanner scanner = new Scanner(System.in);     
        String auctionName;
        
        System.out.println("*** Crazy Auctions :: Sales :: View Product Details :: Assign Winning Bid ***\n");
        System.out.println("Enter Name of auction listing >");
        auctionName = scanner.nextLine().trim();
        auctionListingSessionBeanRemote.assignWinningBid(auctionName);
    
    }
    
}
