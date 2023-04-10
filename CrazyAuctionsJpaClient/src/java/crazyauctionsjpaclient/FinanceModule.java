/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaclient;

import java.util.Scanner;

/**
 *
 * @author xinyi
 */
public class FinanceModule {
    public void FinanceOperation()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Crazy Auctions :: Finance Operation ***\n");
            System.out.println("1: Create Credit Package");
            System.out.println("2: View Credit Package Details");
            System.out.println("3: Update Credit Package");
            System.out.println("4: Delete Credit Package");
            System.out.println("5: View All Credit Package");
            System.out.println("6: Back\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                   createCreditPackage();
                }
                else if(response == 2)
                {
                    viewCreditPackageDetails();
                }
                else if (response == 3)
                {
                    updateCreditPackage();
                } else if (response == 4) {
                    deleteCreditPackage();
                } else if (response == 5){
                    viewAllCreditPackages();
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 6)
            {
                break;
            }
        }
    }
 
    
    public void createCreditPackage(){}
    
    public void viewCreditPackageDetails(){}
    
    public void updateCreditPackage(){}
    
    public void deleteCreditPackage(){}
    
    public void viewAllCreditPackages(){}
}
