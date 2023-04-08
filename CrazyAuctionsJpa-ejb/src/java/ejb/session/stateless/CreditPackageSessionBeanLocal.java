/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditPackage;
import java.math.BigDecimal;
import javax.ejb.Local;
import util.exception.CreditTransactionHistoryNotFoundException;

/**
 *
 * @author chiaangyong
 */
@Local
public interface CreditPackageSessionBeanLocal {
    public Long createNewCreditPackage(BigDecimal creditPrice, String creditPackageType, BigDecimal creditPackageQuantity);
    public Long createNewCreditPackage(CreditPackage creditPackage);
    public CreditPackage retrieveCreditTransactionHistory(String username) throws CreditTransactionHistoryNotFoundException;
}
