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
import javax.ejb.Remote;
import util.exception.CreditPackageNotFoundException;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.DeleteCreditPackageException;
import util.exception.UpdateCreditPackageException;

/**
 *
 * @author chiaangyong
 */
@Remote
public interface CreditPackageSessionBeanRemote {
    public Long createNewCreditPackage(BigDecimal creditPrice, String creditPackageType, Boolean isActive);
    public Long createNewCreditPackage(CreditPackage creditPackage);
    public List<CreditPackage> retrieveCreditTransactionHistory(Customer customer) throws CreditTransactionHistoryNotFoundException;
    public List<CreditPackage> retrieveCreditPackage() throws CreditTransactionHistoryNotFoundException;
    public CreditPackage retrieveCreditPackageById(Long creditPackageId) throws CreditPackageNotFoundException ;
    public void updateCreditPackage(CreditPackage creditPackage) throws CreditPackageNotFoundException, UpdateCreditPackageException;
    public void deleteCreditPackage(Long creditPackageId) throws CreditPackageNotFoundException, DeleteCreditPackageException;
}
