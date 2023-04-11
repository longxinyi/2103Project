/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.CreditPackage;
import entity.Customer;
import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import util.enumeration.AccessRightEnum;
import util.exception.CreditPackageNotFoundException;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.DeleteCreditPackageException;
import util.exception.InvalidAccessRightException;
import util.exception.UpdateCreditPackageException;

/**
 *
 * @author xinyi
 */
public class FinanceModule {
    
    private Employee currentEmployee;
    private CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

    public FinanceModule() {
    }
    public FinanceModule(CreditPackageSessionBeanRemote creditPackageSessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote, Employee currentEmployee) 
    {
        this.creditPackageSessionBeanRemote = creditPackageSessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.currentEmployee = currentEmployee;
    }
    
    public void financeOperation() throws InvalidAccessRightException, CreditTransactionHistoryNotFoundException, CreditPackageNotFoundException, UpdateCreditPackageException, DeleteCreditPackageException
    {
        if(currentEmployee.getAccessRightEnum() != AccessRightEnum.FINANCE)
        {
            throw new InvalidAccessRightException("You don't have FINANCE rights to access the system administration module.");
        }
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
 
    
    public void createCreditPackage(){
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        BigDecimal price = new BigDecimal(0);
        BigDecimal quantity = new BigDecimal(0);
        while (true) {
            response = 0;
            quantity = new BigDecimal(0);
            price = new BigDecimal(0);
            System.out.println("*** Crazy Auction Create Credit Packages ***\n");
            System.out.println("1: Key in Price of Credit Package");
            System.out.println("2: Exit\n");
            
            while (response < 1 || response > 2) {
                System.out.print("> ");
                response = scanner.nextInt();
                if(response ==1){
                    System.out.println("What's the price set for Credit Package ?");
                    System.out.print("> ");
                    price = scanner.nextBigDecimal();
                    Long creditPackageId = creditPackageSessionBeanRemote.createNewCreditPackage(price, null, false);
                } else if (response == 2) {
                    break;
                
                }
            
            }
            if (response == 2) {
                break;
            }
        }
    }
    
    public void viewCreditPackageDetails() throws CreditTransactionHistoryNotFoundException{
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        System.out.println("*** View Credit Package Details ***");
        System.out.println("Enter the credit package id to check");
        System.out.print(">");
        int creditPackageId = scanner.nextInt();
        List<CreditPackage> listOfCreditPackage = creditPackageSessionBeanRemote.retrieveCreditPackage();
        for (CreditPackage creditPackage : listOfCreditPackage) {
            if(creditPackage.getCreditPackageId() == creditPackageId){
                System.out.println("Name of Credit Package : Credit Package " + creditPackage.getCreditPackageId()+ " with the price of: $" + creditPackage.getCreditPrice() + " \n");
        
            }   
        }
    
    }
    
    public void updateCreditPackage() throws CreditTransactionHistoryNotFoundException, CreditPackageNotFoundException, UpdateCreditPackageException{
       Scanner scanner = new Scanner(System.in);
       Integer response = 0;
       System.out.println("*** Update Credit Package ***");
       System.out.println("Enter the credit package id to update");
       int creditPackageId = scanner.nextInt();
       List<CreditPackage> listOfCreditPackage = creditPackageSessionBeanRemote.retrieveCreditPackage();
        for (CreditPackage creditPackage : listOfCreditPackage) {
            if(creditPackage.getCreditPackageId() == creditPackageId){
               System.out.println("Enter the new price");
               System.out.print(">");
               BigDecimal newPrice = scanner.nextBigDecimal();
               creditPackage.setCreditPrice(newPrice);
               response=1; 
               creditPackageSessionBeanRemote.updateCreditPackage(creditPackage);
                
            }
        }
        if (response==0){
            System.out.println("Invalid Package Id inputted");
        }
        
        
        
    }
    
    public void deleteCreditPackage() throws CreditPackageNotFoundException, DeleteCreditPackageException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Remove Credit Package ***");
        System.out.println("Enter the credit package id to update");
        Long creditPackageId = scanner.nextLong();
        creditPackageSessionBeanRemote.deleteCreditPackage(creditPackageId);
        
        System.out.println("Delete Credit Package "+creditPackageId+" Successfully");
    }
    
    public void viewAllCreditPackages() throws CreditTransactionHistoryNotFoundException{
        List<CreditPackage> listOfCreditPackage = creditPackageSessionBeanRemote.retrieveCreditPackage();
        for (CreditPackage creditPackage : listOfCreditPackage) {
            System.out.println("Name of Credit Package : Credit Package " + creditPackage.getCreditPackageId()+ " with the price of: $" + creditPackage.getCreditPrice() + " \n");
        }   
        
    }
}
