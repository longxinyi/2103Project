/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;
import entity.CreditPackage;
import entity.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author chiaangyong
 */
@Stateless
public class CreditPackageSessionBean implements CreditPackageSessionBeanRemote, CreditPackageSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCreditPackage(CreditPackage creditPackage){
        em.persist(creditPackage);
        em.flush();
        return creditPackage.getCreditPackageId();
    }
   
    @Override
    public Long createNewCreditPackage(BigDecimal creditPrice, String creditPackageType, BigDecimal creditPackageQuantity){
        CreditPackage creditPackage = new CreditPackage(creditPrice, creditPackageType, creditPackageQuantity);
        em.persist(creditPackage);
        em.flush();
        
        return creditPackage.getCreditPackageId();
    }
    
    @Override
    public List<CreditPackage> retrieveCreditTransactionHistory(Customer customer) throws CreditTransactionHistoryNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM CreditPackage c ");
        //query.setParameter("inCustomer", customer);
        
        try
        {
            return (List<CreditPackage>)query.getResultList();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CreditTransactionHistoryNotFoundException("Credit Transaction History " + " does not exist! Please Register!");
        }
    };
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
}
