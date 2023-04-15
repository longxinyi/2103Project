/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.AuctionListingSessionBeanRemote;
import entity.AuctionListing;
import entity.AuctionListingBid;
import entity.Customer;
import entity.Employee;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.AccessRightEnum;
import util.exception.ListingNotFoundException;
import util.exception.InvalidAccessRightException;
import util.exception.ListingExistException;
import util.exception.ListingNotFoundException;
import util.exception.NoBidException;
import util.exception.WrongDateException;

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

    public void salesOperation() throws InvalidAccessRightException, ListingNotFoundException, ParseException, WrongDateException, NoBidException {
        if (currentEmployee.getAccessRightEnum() != AccessRightEnum.SALES) {
            throw new InvalidAccessRightException("You don't have SALES rights to access the system administration module.");
        }
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
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

            while (response < 1 || response > 8) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    createAuctionListing();
                } else if (response == 2) {
                    viewAuctionListingDetails();
                } else if (response == 3) {
                    updateAuctionListing();

                } else if (response == 4) {

                    deleteAuctionListing();

                } else if (response == 5) {

                    viewAllAuctionListing();

                } else if (response == 6) {

                    viewAllAuctionListingsBelowReservePrice();

                } else if (response == 7) {

                    //assignWinningBidForListingsWBidsBelowReservePrice();

                } else if (response == 8) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 8) {
                break;
            }
        }

    }

    public void createAuctionListing() throws ListingNotFoundException, ParseException, WrongDateException {
        //not visible to customers until start date time is reached
        Scanner scanner = new Scanner(System.in);
        AuctionListing newAuctionListing = new AuctionListing();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("*** Crazy Auctions :: Sales :: Create New Auction Listing ***\n");
        System.out.print("Enter Auction Name > ");
        String auctionName = scanner.nextLine().trim();
        newAuctionListing.setAuctionName(auctionName);
        
        System.out.print("Enter Reserve Price > ");
        newAuctionListing.setReservePrice(new BigDecimal(scanner.nextLine().trim()));
        
        try {
            System.out.print("Enter Start Date Time (YYYY-MM-DD HH:MM:SS) > ");
            newAuctionListing.setStartDateTime(format.parse(scanner.nextLine().trim()));
        } catch (ParseException e){
            System.out.println("Invalid date and time! please enter in the correct format!");
        }
        try{
            System.out.print("Enter End Date Time (YYYY-MM-DD HH:MM:SS) > ");
            Date newEndDateTime = format.parse(scanner.nextLine().trim());
            newAuctionListing.setEndDateTime(newEndDateTime);
            
            if(newAuctionListing.getStartDateTime().before(newEndDateTime)){
                newAuctionListing.setEndDateTime(newEndDateTime);
            } else {
                throw new WrongDateException("Ending Date cannot be before Starting Date");
            }
            
            
        } catch(ParseException e){
            throw new ParseException("Invalid date and time! please enter in the correct format!", e.getErrorOffset());
        }
        newAuctionListing.setActive(false);
        

        Long newAuctionListingId = auctionListingSessionBeanRemote.createNewAuctionListing(newAuctionListing);

        System.out.println("New auction listing created successfully!: " + newAuctionListing.getAuctionListingId() + "\n");

    }

    public void viewAuctionListingDetails() throws ListingNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter auction listing name > ");
        String auctionListingName = scanner.nextLine().trim();
        AuctionListing auctionListing;
        try {
            auctionListing = auctionListingSessionBeanRemote.findListingByName(auctionListingName);
        } catch (ListingNotFoundException e) {
            throw new ListingNotFoundException("Listing not found, please try again");
        }
        
        try {
            AuctionListingBid highestBid = auctionListingSessionBeanRemote.getHighestBid(auctionListingName);
            System.out.println("Auction Listing Name: " + auctionListing.getAuctionName() + " with the current highest bid of: " + highestBid.getBidPrice());
        } catch (NoBidException e) {
                System.out.println("Auction Listing Name: " + auctionListing.getAuctionName() + " has no bids currently!");
        }
       
    
    }

    public void updateAuctionListing() throws ParseException, WrongDateException {
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

                response = scanner.nextInt();
                if (response == 1) {

                    try {
                        System.out.print("Enter New Auction Name > ");
                        String newAuctionName = newScanner.nextLine().trim();
                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newAuctionName, 1);
                        auctionListingName = newAuctionName;
                        System.out.print("Name has been successfully changed! ");
                    } catch (ListingNotFoundException e) {
                        System.out.print("There was an error, please try again ");
                    }

                    

                } else if (response == 2) {
                    try {
                        System.out.print("Enter New Reserve Price > ");
                        String newReservePrice = newScanner.nextLine().trim();
                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newReservePrice, 2);
                        System.out.print("Reserve Price has been successfully changed! ");
                    } catch (ListingNotFoundException e) {
                        System.out.print("There was an error, please try again ");
                    }

                    

                } else if (response == 3) {
                    try {
                        System.out.print("Enter New Start Date Time (YYYY-MM-DD HH:MM:SS) > ");
                        String newStartDateTime = newScanner.nextLine().trim();
                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newStartDateTime, 3);
                        System.out.print("Start Date Time has been successfully changed! ");
                    } catch (ListingNotFoundException e) {
                        System.out.print("There was an error, please try again ");
                    }

                    

                } else if (response == 4) {
                    try {
                        System.out.print("Enter New End Date Time (YYYY-MM-DD HH:MM:SS) > ");
                        String newEndDateTime = newScanner.nextLine().trim();
                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newEndDateTime, 4);
                        System.out.print("End Date Time has been successfully changed! ");
                    } catch (ListingNotFoundException e) {
                        System.out.print("There was an error, please try again ");
                    }

                    

                } else if (response == 5) {

                    try {
                        System.out.print("Enter New Active Status > ");
                        String newActiveStatus = newScanner.nextLine().trim();
                        auctionListingSessionBeanRemote.updateAuctionListing(auctionListingName, newActiveStatus, 5);
                        System.out.print("Active Status has been successfully changed! ");
                    } catch (ListingNotFoundException e) {
                        System.out.print("There was an error, please try again ");
                    }

                    

                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }

            }

            if (response == 6) {
                break;
            }

        }
    }

    public void deleteAuctionListing() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Crazy Auctions :: Sales :: View Product Details :: Delete Product ***\n");
        System.out.println("Enter Name of auction you wish to delete >");
        input = scanner.nextLine().trim();

        try {
            auctionListingSessionBeanRemote.deleteAuctionListing(input);
            System.out.println("Listing deleted successfully!");
        } catch (ListingNotFoundException e) {
            System.out.println("Listing not found!");
        }
    }

    public void viewAllAuctionListing() throws ListingNotFoundException {
        try {
            List<AuctionListing> activeAuctions = auctionListingSessionBeanRemote.retrieveAuctionListing();
            
            for (AuctionListing auctionListing : activeAuctions) {
                System.out.println("Name of listing : " + auctionListing.getAuctionName() + " with the current highest bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size()) + " \n");
            }
        } catch (ListingNotFoundException e) {
            throw new ListingNotFoundException("No Listings found!");
        }
    }

    public void viewAllAuctionListingsBelowReservePrice() throws ListingNotFoundException, NoBidException {
        //filter to show only expired or closed listings
        List<AuctionListing> belowReservePrice = auctionListingSessionBeanRemote.viewAuctionListingsBelowReservePrice();
        
        for (AuctionListing listing : belowReservePrice){
            System.out.println("This listing: " + listing.getAuctionName() + " only has bids below its reserve price!");
        }

    }

//    public void assignWinningBidForListingsWBidsBelowReservePrice() throws ListingNotFoundException {
//        Scanner scanner = new Scanner(System.in);
//        String auctionName;
//
//        System.out.println("*** Crazy Auctions :: Sales :: View Product Details :: Assign Winning Bid ***\n");
//        System.out.println("Enter Name of auction listing >");
//        auctionName = scanner.nextLine().trim();
//        AuctionListing currListing = auctionListingSessionBeanRemote.findListingByName(auctionName);
//        
//        List<AuctionListingBid> bids = currListing.getAuctionListingBids();
//        for(AuctionListingBid bid : bids){
//            System.out.println("This bid is made by " + bid.getCustomer().getUsername() + " of price: " + bid.getBidPrice());
//        }
//        
//        System.out.println("Enter Name of winning customer >");
//        String customerName = scanner.nextLine().trim();
//        
//        Customer winningCustomer = customerSessionBeanRemote.retrieveCustomerByUsername(customerName);
//       
//
//        winningCustomer.getListOfWonAuctionListings().add(currListing);
//        
//
//    }

}
