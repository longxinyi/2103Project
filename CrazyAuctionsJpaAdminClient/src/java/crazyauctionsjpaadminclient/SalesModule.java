/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import java.util.Scanner;

/**
 *
 * @author xinyi
 */
public class SalesModule {

    public SalesModule() {
    }
    
    
    
    
    public void salesOperation()
    {
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
    public void viewAuctionListingDetails(){}
    public void updateAuctionListing(){}
    public void deleteAuctionListing(){}
    public void viewAllAuctionListing(){}
    public void assignWinningBid(){}
    
}
