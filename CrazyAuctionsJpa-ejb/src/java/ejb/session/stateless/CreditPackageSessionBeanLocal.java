/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditPackage;
import entity.Customer;
import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreditPackageNotFoundException;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.UpdateCreditPackageException;

/**
 *
 * @author chiaangyong
 */
@Local
public interface CreditPackageSessionBeanLocal {
    public Long createNewCreditPackage(BigDecimal creditPrice, String creditPackageType, Boolean isActive);
    public Long createNewCreditPackage(CreditPackage creditPackage);
    public List<CreditPackage> retrieveCreditTransactionHistory(Customer customer) throws CreditTransactionHistoryNotFoundException;
    public List<CreditPackage> retrieveCreditPackage() throws CreditTransactionHistoryNotFoundException;
    public void updateCreditPackage(CreditPackage creditPackage) throws CreditPackageNotFoundException, UpdateCreditPackageException;
}
